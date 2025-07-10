package com.example.cocktails.service;

import com.example.cocktails.model.dto.response.AuthenticationResponse;
import com.example.cocktails.model.dto.user.UserLoginDto;
import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.model.email.AccountVerificationEmailContext;
import com.example.cocktails.model.entity.RoleEntity;
import com.example.cocktails.model.entity.SecureTokenEntity;
import com.example.cocktails.model.entity.UserEntity;
import com.example.cocktails.model.entity.enums.RoleNameEnum;
import com.example.cocktails.model.mapper.UserMapper;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.repository.RoleRepository;
import com.example.cocktails.repository.SecureTokenRepository;
import com.example.cocktails.repository.UserRepository;
import com.example.cocktails.web.exception.InvalidTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final SecureTokenService secureTokenService;
  private final SecureTokenRepository secureTokenRepository;
  private final EmailService emailService;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
      RoleRepository roleRepository, SecureTokenService secureTokenService,
      SecureTokenRepository secureTokenRepository, EmailService emailService,
      AuthenticationManager authenticationManager,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.secureTokenService = secureTokenService;
    this.secureTokenRepository = secureTokenRepository;
    this.emailService = emailService;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public void register(UserRegisterDto userRegisterDto, Locale preferedLocale) {
    UserEntity newUser = userMapper.userRegisterDtoToUserEntity(userRegisterDto);
    newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
    newUser.addRole(getUserRole());
    newUser.setAccountVerified(false);
    sendVerificationMail(this.userRepository.save(newUser), userRegisterDto.getBaseUrl(), preferedLocale);
  }

  public void sendVerificationMail(UserEntity newUser, String baseURL, Locale preferedLocale) {
    SecureTokenEntity token = secureTokenService.createSecureToken(newUser);

    AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
    emailContext.setToken(token.getToken());
    emailContext.setLocale(preferedLocale);
    emailContext.setBaseUrl(baseURL);
    emailContext.initContext(newUser);

    emailService.sendEmail(emailContext);
  }

  public void verifyUser(String token) {
    Optional<SecureTokenEntity> tokenOpt = secureTokenRepository.findByToken(token);
    if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
      throw new InvalidTokenException();
    }

    UserEntity user = tokenOpt.get().getUser();
    if (user == null) {
      throw new InvalidTokenException();
    }

    user.setAccountVerified(true);
    userRepository.save(user);
    secureTokenRepository.delete(tokenOpt.get());
  }

  public AuthenticationResponse authenticate(UserLoginDto userLoginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userLoginDto.username(),
            userLoginDto.password()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

    return new AuthenticationResponse(
        userPrincipal.getId(),
        userPrincipal.getUsername(),
        jwtService.generateToken(userPrincipal)
    );
  }

  private RoleEntity getUserRole() {
    return roleRepository.findByRole(RoleNameEnum.USER).orElseThrow();
  }
}

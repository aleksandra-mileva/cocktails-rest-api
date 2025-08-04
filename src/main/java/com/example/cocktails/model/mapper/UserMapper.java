package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.response.AuthenticationResponse;
import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.model.entity.UserEntity;
import com.example.cocktails.model.user.CustomUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "addedCocktails", ignore = true)
  @Mapping(target = "accountVerified", ignore = true)
  @Mapping(target = "createdOn", ignore = true)
  @Mapping(target = "favorites", ignore = true)
  @Mapping(target = "addedPictures", ignore = true)
  UserEntity userRegisterDtoToUserEntity(UserRegisterDto userRegisterDto);

  @Mapping(target = "authorities", expression = "java(mapAuthorities(userDetails.getAuthorities()))")
  AuthenticationResponse userDetailsToAuthenticationResponse(CustomUserDetails userDetails);

  default List<String> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
    return authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .filter(auth -> auth.startsWith("ROLE_"))
        .map(auth -> auth.substring("ROLE_".length()))
        .collect(Collectors.toList());
  }
}

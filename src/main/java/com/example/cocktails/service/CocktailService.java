package com.example.cocktails.service;

import com.example.cocktails.model.dto.cocktail.AddCocktailDto;
import com.example.cocktails.model.dto.cocktail.CocktailDetailsViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.entity.UserEntity;
import com.example.cocktails.model.entity.enums.RoleNameEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import com.example.cocktails.model.mapper.CocktailMapper;
import com.example.cocktails.model.mapper.PictureMapper;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.CocktailSpecification;
import com.example.cocktails.repository.UserRepository;
import com.example.cocktails.web.exception.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CocktailService {

  private final CocktailRepository cocktailRepository;
  private final UserRepository userRepository;
  private final PictureService pictureService;
  private final CocktailMapper cocktailMapper;
  private final PictureMapper pictureMapper;

  public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository,
      PictureService pictureService, CocktailMapper cocktailMapper, PictureMapper pictureMapper) {
    this.cocktailRepository = cocktailRepository;
    this.userRepository = userRepository;
    this.pictureService = pictureService;
    this.cocktailMapper = cocktailMapper;
    this.pictureMapper = pictureMapper;
  }

  public PagedModel<CocktailViewModel> findAllCocktailsUploadedByUserId(Long userId, Pageable pageable) {
    Page<CocktailViewModel> result = this.cocktailRepository.
        findAllByAuthor_Id(userId, pageable);
    return new PagedModel<>(result);
  }

  public PagedModel<CocktailViewModel> findAllFavoriteCocktailsForUserId(Long userId, Pageable pageable) {
    Page<CocktailViewModel> result = this.cocktailRepository.
        findFavoriteCocktailsByUserId(userId, pageable);

    return new PagedModel<>(result);
  }

  public PagedModel<CocktailViewModel> searchCocktail(
      SearchCocktailDto searchCocktailDto,
      Pageable pageable
  ) {
    CocktailSpecification cocktailSpecification = new CocktailSpecification(searchCocktailDto);

    Page<CocktailViewModel> result = this.cocktailRepository
        .findAll(cocktailSpecification, pageable)
        .map(this::mapToCocktailViewModel);

    return new PagedModel<>(result);
  }

  @Transactional
  public Long addCocktail(AddCocktailDto addCocktailDto, MultipartFile file, CustomUserDetails userDetails) {
    CocktailEntity newCocktail = cocktailMapper.addCocktailDtoToCocktailEntity(addCocktailDto);
    newCocktail.setAuthor(userRepository.findById(userDetails.getId()).orElseThrow());

    if (file != null && !file.isEmpty()) {
      PictureEntity picture = pictureService.createAndSavePictureEntity(userDetails.getId(), file);
      newCocktail.setPicture(picture);
    }

    return this.cocktailRepository.save(newCocktail).getId();
  }

  public CocktailDetailsViewModel findCocktailDetailsViewModelById(Long id, CustomUserDetails userDetails) {
    CocktailEntity cocktailEntity = this.cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with ID " + id + " not found!"));

    CocktailDetailsViewModel cocktailDetailsViewModel = cocktailMapper.entityToDetailsViewModel(cocktailEntity);
    cocktailDetailsViewModel.setIngredients(Arrays.stream(cocktailEntity.getIngredients().split("[\r\n]+"))
        .collect(Collectors.toList()));
    cocktailDetailsViewModel.setAuthor(
        cocktailEntity.getAuthor().getFirstName() + " " + cocktailEntity.getAuthor().getLastName());
    cocktailDetailsViewModel.setVideoId(extractVideoId(cocktailEntity.getVideoUrl()));
    cocktailDetailsViewModel.setPicture(pictureMapper.pictureEntityToPictureViewModel(cocktailEntity.getPicture()));

    String username = userDetails != null ? userDetails.getUsername() : null;
    cocktailDetailsViewModel.setCanDelete(username != null && isOwner(username, id));

    cocktailDetailsViewModel.setIsFavorite(
        isCocktailFavorite(username, cocktailEntity.getId()));

    return cocktailDetailsViewModel;
  }

  @Transactional
  public void updateCocktail(Long cocktailId, AddCocktailDto addCocktailDto, MultipartFile file,
      CustomUserDetails userDetails) {
    CocktailEntity updateCocktail = this.cocktailRepository.findById(cocktailId)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with id: " + cocktailId + " not found!"));

    updateCocktail.setName(addCocktailDto.name())
        .setIngredients(addCocktailDto.ingredients())
        .setPreparation(addCocktailDto.preparation())
        .setFlavour(addCocktailDto.flavour())
        .setVideoUrl(addCocktailDto.videoUrl())
        .setType(addCocktailDto.type())
        .setSpirit(addCocktailDto.spirit())
        .setPercentAlcohol(addCocktailDto.percentAlcohol())
        .setServings(addCocktailDto.servings());

    if (file != null && !file.isEmpty()) {
      PictureEntity picture = pictureService.createAndSavePictureEntity(userDetails.getId(), file);
      updateCocktail.setPicture(picture);
    }

    this.cocktailRepository.save(updateCocktail);
  }

  public List<CocktailHomePageViewModel> getThreeRandomCocktailsByType(TypeNameEnum type) {
    return cocktailRepository.getRandomCocktailsByType(type, PageRequest.of(0, 3));
  }

  @Transactional
  public void deleteCocktailById(Long cocktailId) {
    CocktailEntity cocktail = cocktailRepository.findById(cocktailId)
        .orElseThrow(
            () -> new ObjectNotFoundException("Cocktail with ID " + cocktailId + " not found!"));

    cocktail.getFavoriteUsers().forEach(user -> {
      user.getFavorites().remove(cocktail);
      userRepository.save(user);
    });
    cocktailRepository.deleteById(cocktailId);
  }

  public long findCountBySpirit(SpiritNameEnum spiritNameEnum) {
    return this.cocktailRepository.countCocktailEntitiesBySpirit(spiritNameEnum);
  }

  public long findCountAll() {
    return this.cocktailRepository.count();
  }

  public boolean isOwner(String userName, Long cocktailId) {
    boolean isOwner = cocktailRepository.
        findById(cocktailId).
        filter(r -> r.getAuthor().getUsername().equals(userName)).
        isPresent();

    if (isOwner) {
      return true;
    }

    return userRepository
        .findByUsername(userName)
        .filter(this::isAdmin)
        .isPresent();
  }

  private boolean isAdmin(UserEntity user) {
    return user.getRoles().
        stream().
        anyMatch(r -> r.getRole() == RoleNameEnum.ADMIN);
  }

  private CocktailViewModel mapToCocktailViewModel(CocktailEntity cocktail) {
    CocktailViewModel cocktailViewModel = cocktailMapper.cocktailEntityToCocktailViewModel(cocktail);
    cocktailViewModel.setAuthor(
        cocktail.getAuthor().getFirstName() + " " + cocktail.getAuthor().getLastName());
    return cocktailViewModel;
  }

  private static String extractVideoId(String videoUrl) {
    String pattern =
        "(?<=v=|\\/videos\\/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|%2Fvideos%2F|%2Fvi%2F|v=|%2Fv%2F)([a-zA-Z0-9_-]{11})";
    Pattern compiledPattern = Pattern.compile(pattern);
    Matcher matcher = compiledPattern.matcher(videoUrl);

    if (matcher.find()) {
      return matcher.group();
    }

    return null;
  }

  private boolean isCocktailFavorite(String username, Long cocktailId) {
    UserEntity user = this.userRepository.findByUsername(username).orElse(null);

    if (user == null) {
      return false;
    }

    return user.getFavorites().stream().map(CocktailEntity::getId)
        .anyMatch(id -> id.equals(cocktailId));
  }
}

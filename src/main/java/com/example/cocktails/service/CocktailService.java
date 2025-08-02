package com.example.cocktails.service;

import com.example.cocktails.model.dto.cocktail.AddCocktailDto;
import com.example.cocktails.model.dto.cocktail.CocktailDetailsViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.dto.comment.AddCommentDto;
import com.example.cocktails.model.dto.comment.CommentViewModel;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.CommentEntity;
import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.entity.enums.RoleNameEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import com.example.cocktails.model.mapper.CocktailMapper;
import com.example.cocktails.model.mapper.CommentMapper;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.CocktailSpecification;
import com.example.cocktails.repository.CommentRepository;
import com.example.cocktails.repository.UserRepository;
import com.example.cocktails.web.exception.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CocktailService {

  private final CocktailRepository cocktailRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final PictureService pictureService;
  private final CocktailMapper cocktailMapper;
  private final CommentMapper commentMapper;

  public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository,
      PictureService pictureService, CocktailMapper cocktailMapper, CommentRepository commentRepository,
      CommentMapper commentMapper) {
    this.cocktailRepository = cocktailRepository;
    this.userRepository = userRepository;
    this.pictureService = pictureService;
    this.cocktailMapper = cocktailMapper;
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
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
  public CocktailDetailsViewModel addCocktail(AddCocktailDto addCocktailDto, MultipartFile file, CustomUserDetails userDetails) {
    CocktailEntity newCocktail = cocktailMapper.addCocktailDtoToCocktailEntity(addCocktailDto);
    newCocktail.setAuthor(userRepository.findById(userDetails.getId()).orElseThrow());

    if (file != null && !file.isEmpty()) {
      PictureEntity picture = pictureService.createAndSavePictureEntity(userDetails.getId(), file);
      newCocktail.setPicture(picture);
    }

    CocktailDetailsViewModel vm =
        cocktailMapper.cocktailEntityToDetailsViewModel(this.cocktailRepository.save(newCocktail));

    return enhanceCocktailDetailsViewModel(vm, userDetails, true);
  }

  public CocktailDetailsViewModel findCocktailDetailsViewModelById(Long id, CustomUserDetails userDetails) {
    CocktailDetailsViewModel vm = cocktailRepository.findCocktailDetailsById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with ID " + id + " not found!"));

    Long userId = userDetails != null ? userDetails.getId() : null;
    boolean canDeleteCocktail = userId != null && isOwnerOrAdminOfCocktail(userId, id);

    return enhanceCocktailDetailsViewModel(vm, userDetails, canDeleteCocktail);
  }

  @Transactional
  public CocktailDetailsViewModel updateCocktail(Long id, AddCocktailDto addCocktailDto, MultipartFile file,
      CustomUserDetails userDetails) {
    CocktailEntity updateCocktail = this.cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with id: " + id + " not found!"));

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

    CocktailDetailsViewModel vm =
        cocktailMapper.cocktailEntityToDetailsViewModel(this.cocktailRepository.save(updateCocktail));

    return enhanceCocktailDetailsViewModel(vm, userDetails, true);
  }

  public CocktailHomePageViewModel getRandomCocktailByType(TypeNameEnum type) {
    return cocktailRepository.getRandomCocktailByType(type, PageRequest.of(0, 1))
        .stream().findFirst().orElse(null);
  }

  @Transactional
  public void deleteCocktailById(Long id) {
    CocktailEntity cocktail = cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with ID " + id + " not found!"));

    cocktail.getFavoriteUsers().forEach(user -> {
      user.getFavorites().remove(cocktail);
      userRepository.save(user);
    });
    cocktailRepository.deleteById(id);
  }

  public List<CommentViewModel> getCommentsByCocktailId(Long id, CustomUserDetails userDetails) {
    if (!cocktailRepository.existsById(id)) {
      throw new ObjectNotFoundException("Cocktail with ID " + id + " not found!");
    }

    Long userId = userDetails != null ? userDetails.getId() : null;

    return commentRepository.findByCocktailId(id)
        .stream()
        .map(comment ->
            comment.setCanDelete(userId != null && isOwnerOrAdminOfComment(userId, comment.getId())))
        .toList();
  }

  public CommentViewModel addComment(Long id, AddCommentDto addCommentDto, CustomUserDetails userDetails) {
    CocktailEntity cocktail = cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with ID " + id + " not found!"));

    CommentEntity newComment = new CommentEntity()
        .setText(addCommentDto.text())
        .setCreated(LocalDateTime.now())
        .setAuthor(userRepository.findById(userDetails.getId()).orElseThrow())
        .setCocktail(cocktail);

    CommentViewModel commentViewModel = commentMapper.commentToCommentViewModel(commentRepository.save(newComment));
    commentViewModel.setCanDelete(true);
    return commentViewModel;
  }

  public void deleteCommentById(Long cocktailId, Long commentId) {
    CommentEntity commentEntity = commentRepository.findById(commentId)
        .orElseThrow(() -> new ObjectNotFoundException("Comment with ID " + commentId + " not found!"));

    if (!Objects.equals(commentEntity.getCocktail().getId(), cocktailId)) {
      throw new IllegalArgumentException("Comment does not belong to this cocktail.");
    }
    commentRepository.delete(commentEntity);
  }

  public long findCountBySpirit(SpiritNameEnum spiritNameEnum) {
    return this.cocktailRepository.countCocktailEntitiesBySpirit(spiritNameEnum);
  }

  public long findCountAll() {
    return this.cocktailRepository.count();
  }

  public boolean isOwnerOrAdminOfCocktail(Long userId, Long cocktailId) {
    return cocktailRepository.existsByIdAndAuthor_Id(cocktailId, userId) ||
        userRepository.existsByUserIdAndRole(userId, RoleNameEnum.ADMIN);
  }

  public boolean isOwnerOrAdminOfComment(Long userId, Long commentId) {
    return commentRepository.existsByIdAndAuthor_Id(commentId, userId) ||
        userRepository.existsByUserIdAndRole(userId, RoleNameEnum.ADMIN);
  }

  private CocktailViewModel mapToCocktailViewModel(CocktailEntity cocktail) {
    CocktailViewModel cocktailViewModel = cocktailMapper.cocktailEntityToCocktailViewModel(cocktail);
    cocktailViewModel.setAuthor(
        cocktail.getAuthor().getFirstName() + " " + cocktail.getAuthor().getLastName());
    return cocktailViewModel;
  }

  private CocktailDetailsViewModel enhanceCocktailDetailsViewModel(CocktailDetailsViewModel vm,
      CustomUserDetails userDetails, boolean canDeleteCocktail) {

    vm.setIngredients(Arrays.stream(vm.getIngredientsRaw().split("[\r\n]+")).toList());
    vm.setAuthor(vm.getAuthorFirstName() + " " + vm.getAuthorLastName());
    vm.setVideoId(extractVideoId(vm.getVideoUrl()));

    Long userId = userDetails != null ? userDetails.getId() : null;

    List<CommentViewModel> comments = commentRepository.findByCocktailId(vm.getId())
        .stream()
        .map(comment ->
            comment.setCanDelete(userId != null && isOwnerOrAdminOfComment(userId, comment.getId())))
        .toList();
    vm.setComments(comments);

    vm.setCanDelete(canDeleteCocktail);
    vm.setFavorite(isCocktailFavorite(userId, vm.getId()));

    return vm;
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

  private boolean isCocktailFavorite(Long userId, Long cocktailId) {
    if (userId == null) {
      return false;
    }

    return userRepository.isCocktailInUserFavorites(userId, cocktailId);
  }
}

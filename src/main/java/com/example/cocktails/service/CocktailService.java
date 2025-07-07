package com.example.cocktails.service;

import com.example.cocktails.model.dto.cocktail.AddCocktailDto;
import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.mapper.CocktailMapper;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.CocktailSpecification;
import com.example.cocktails.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CocktailService {

  private final CocktailRepository cocktailRepository;
  private final UserRepository userRepository;
  private final PictureService pictureService;
  private final CocktailMapper cocktailMapper;

  public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository,
      PictureService pictureService, CocktailMapper cocktailMapper) {
    this.cocktailRepository = cocktailRepository;
    this.userRepository = userRepository;
    this.pictureService = pictureService;
    this.cocktailMapper = cocktailMapper;
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

  public void addCocktail(AddCocktailDto addCocktailDto, MultipartFile file, CustomUserDetails userDetails) {
    CocktailEntity newCocktail = cocktailMapper.addCocktailDtoToCocktailEntity(addCocktailDto);
    newCocktail.setAuthor(userRepository.findById(userDetails.getId()).orElseThrow());
    newCocktail = this.cocktailRepository.save(newCocktail);

    if (file != null && !file.isEmpty()) {
      PictureEntity picture = pictureService.createAndSavePictureEntity(userDetails.getId(), file,
          newCocktail.getId());
      newCocktail.setPicture(picture);
    }

    this.cocktailRepository.save(newCocktail);
  }

  public long findCountBySpirit(SpiritNameEnum spiritNameEnum) {
    return this.cocktailRepository.countCocktailEntitiesBySpirit(spiritNameEnum);
  }

  public long findCountAll() {
    return this.cocktailRepository.count();
  }

  private CocktailViewModel mapToCocktailViewModel(CocktailEntity cocktail) {
    CocktailViewModel cocktailViewModel = cocktailMapper.cocktailEntityToCocktailViewModel(cocktail);
    cocktailViewModel.setAuthor(
        cocktail.getAuthor().getFirstName() + " " + cocktail.getAuthor().getLastName());
    return cocktailViewModel;
  }
}

package com.example.cocktails.service;

import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.mapper.CocktailMapper;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.CocktailSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CocktailService {

  private final CocktailRepository cocktailRepository;
  private final CocktailMapper cocktailMapper;

  public CocktailService(CocktailRepository cocktailRepository, CocktailMapper cocktailMapper) {
    this.cocktailRepository = cocktailRepository;
    this.cocktailMapper = cocktailMapper;
  }

  public PagedModel<CocktailViewModel> findAllCocktailsUploadedByUserId(Long id, Pageable pageable) {
    Page<CocktailViewModel> result = this.cocktailRepository.
        findAllByAuthor_Id(id, pageable)
        .map(this::mapToCocktailViewModel);

    return new PagedModel<>(result);
  }

  public PagedModel<CocktailViewModel> findAllFavoriteCocktailsForUserId(Long userId, Pageable pageable) {
    Page<CocktailViewModel> result = this.cocktailRepository.
        findFavoriteCocktailsByUserId(userId, pageable);

    return new PagedModel<>(result);
  }

  public PagedModel<CocktailViewModel> findAllCocktailViewModels(Pageable pageable) {
    Page<CocktailViewModel> result = this.cocktailRepository.
        findAll(pageable)
        .map(this::mapToCocktailViewModel);

    return new PagedModel<>(result);
  }

  public PagedModel<CocktailViewModel> findAllFilteredCocktailViewModels(SpiritNameEnum spirit, Pageable pageable) {
    Page<CocktailViewModel> result = this.cocktailRepository.
        findAllBySpirit(spirit, pageable)
        .map(this::mapToCocktailViewModel);

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
    cocktailViewModel.setPictureUrl(
        !cocktail.getPictures().isEmpty()
            ? cocktail.getPictures()
            .stream()
            .sorted(Comparator.comparingLong(PictureEntity::getId))
            .toList()
            .getFirst()
            .getUrl()
            : "/static/images/register.jpg");
    return cocktailViewModel;
  }
}

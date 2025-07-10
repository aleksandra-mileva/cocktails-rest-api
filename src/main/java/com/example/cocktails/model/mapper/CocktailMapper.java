package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.cocktail.AddCocktailDto;
import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.entity.CocktailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CocktailMapper {

  @Mapping(target = "author", ignore = true)
  @Mapping(target = "pictureUrl", source = "cocktail.picture.url")
  CocktailViewModel cocktailEntityToCocktailViewModel(CocktailEntity cocktail);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "author", ignore = true)
  @Mapping(target = "picture", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "favoriteUsers", ignore = true)
  CocktailEntity addCocktailDtoToCocktailEntity(AddCocktailDto addCocktailDto);
}

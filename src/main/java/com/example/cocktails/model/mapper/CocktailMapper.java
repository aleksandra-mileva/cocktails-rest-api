package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.cocktail.AddCocktailDto;
import com.example.cocktails.model.dto.cocktail.CocktailDetailsViewModel;
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


  @Mapping(target = "ingredientsRaw", source = "cocktail.ingredients")
  @Mapping(target = "authorFirstName", source = "cocktail.author.firstName")
  @Mapping(target = "authorLastName", source = "cocktail.author.lastName")
  @Mapping(target = "pictureUrl", source = "cocktail.picture.url")
  @Mapping(target = "ingredients", ignore = true)
  @Mapping(target = "author", ignore = true)
  @Mapping(target = "videoId", ignore = true)
  @Mapping(target = "picture", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "canDelete", ignore = true)
  @Mapping(target = "favorite", ignore = true)
  CocktailDetailsViewModel cocktailEntityToDetailsViewModel(CocktailEntity cocktail);
}

package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.entity.CocktailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CocktailMapper {

  @Mapping(target = "author", ignore = true)
  @Mapping(target = "pictureUrl", ignore = true)
  CocktailViewModel cocktailEntityToCocktailViewModel(CocktailEntity cocktailEntity);
}

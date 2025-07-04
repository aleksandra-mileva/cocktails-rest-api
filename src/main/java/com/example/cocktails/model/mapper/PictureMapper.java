package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.picture.PictureHomePageViewModel;
import com.example.cocktails.model.entity.PictureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PictureMapper {

  @Mapping(target = "cocktailId", source = "picture.cocktail.id")
  @Mapping(target = "authorFullName", ignore = true)
  PictureHomePageViewModel pictureEntityToPictureHomePageViewModel(PictureEntity picture);
}

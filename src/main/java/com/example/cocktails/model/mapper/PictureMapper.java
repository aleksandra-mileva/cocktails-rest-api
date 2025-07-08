package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.picture.PictureViewModel;
import com.example.cocktails.model.entity.PictureEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PictureMapper {

  PictureViewModel pictureEntityToPictureViewModel(PictureEntity picture);
}

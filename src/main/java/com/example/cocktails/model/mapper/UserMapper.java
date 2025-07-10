package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}

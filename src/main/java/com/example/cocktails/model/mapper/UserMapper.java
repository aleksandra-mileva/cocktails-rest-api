package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.user.UserEditDto;
import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.model.dto.user.UserView;
import com.example.cocktails.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserEntity userRegisterDtoToUserEntity(UserRegisterDto userRegisterDto);

  UserEditDto userEntityToUserEditDto(UserEntity userEntity);

  UserView userEntityToUserViewDto(UserEntity userEntity);
}

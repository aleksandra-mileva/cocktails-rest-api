package com.example.cocktails.model.mapper;

import com.example.cocktails.model.dto.comment.CommentViewModel;
import com.example.cocktails.model.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "author", source = "comment.author.username")
  @Mapping(target = "canDelete", ignore = true)
  CommentViewModel commentToCommentViewModel(CommentEntity comment);
}

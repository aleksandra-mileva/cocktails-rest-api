package com.example.cocktails.repository;

import com.example.cocktails.model.dto.comment.CommentViewModel;
import com.example.cocktails.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


  @Query("""
      select new com.example.cocktails.model.dto.comment.CommentViewModel(
      c.id,
      c.text,
      c.author.firstName,
      c.author.lastName,
      c.created
      ) from CommentEntity c
      where c.cocktail.id = :cocktailId
      """)
  List<CommentViewModel> findByCocktailId(Long cocktailId);

  boolean existsByIdAndAuthor_Id(Long id, Long authorId);
}

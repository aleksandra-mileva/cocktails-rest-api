package com.example.cocktails.repository;

import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

  Page<PictureEntity> findAllByAuthor_Username(String username, Pageable pageable);

  List<PictureEntity> findAllByCocktailType(TypeNameEnum cocktailType);

  void deleteAllById(Long id);
}

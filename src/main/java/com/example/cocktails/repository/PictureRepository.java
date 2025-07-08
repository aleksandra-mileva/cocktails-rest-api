package com.example.cocktails.repository;

import com.example.cocktails.model.entity.PictureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

  Page<PictureEntity> findAllByAuthor_Username(String username, Pageable pageable);

  void deleteAllById(Long id);
}

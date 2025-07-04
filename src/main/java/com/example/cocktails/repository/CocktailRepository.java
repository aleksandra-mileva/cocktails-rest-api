package com.example.cocktails.repository;

import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends JpaRepository<CocktailEntity, Long>,
    JpaSpecificationExecutor<CocktailEntity> {

  Page<CocktailEntity> findAllByAuthor_Id(Long authorId, Pageable pageable);

  Page<CocktailEntity> findAllBySpirit(SpiritNameEnum spiritName, Pageable pageable);

  @Query("""
        SELECT new com.example.cocktails.model.dto.cocktail.CocktailViewModel(
          c.id,
          c.name,
          c.flavour,
          c.spirit,
          c.author.firstName,
          c.author.lastName,
          (SELECT p.url FROM PictureEntity p WHERE p.cocktail = c ORDER BY p.id ASC LIMIT 1),
          c.percentAlcohol,
          c.servings
        )
        FROM CocktailEntity c
        JOIN c.favoriteUsers u
        WHERE u.id = :userId
      """)
  Page<CocktailViewModel> findFavoriteCocktailsByUserId(@Param("userId") Long userId, Pageable pageable);


  long countCocktailEntitiesBySpirit(SpiritNameEnum spirit);
}


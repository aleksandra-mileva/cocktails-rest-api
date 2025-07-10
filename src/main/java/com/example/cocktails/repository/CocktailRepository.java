package com.example.cocktails.repository;

import com.example.cocktails.model.dto.cocktail.CocktailDetailsViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CocktailRepository extends JpaRepository<CocktailEntity, Long>,
    JpaSpecificationExecutor<CocktailEntity> {

  @Query("""
        SELECT new com.example.cocktails.model.dto.cocktail.CocktailViewModel(
          c.id,
          c.name,
          c.flavour,
          c.spirit,
          c.author.firstName,
          c.author.lastName,
          c.picture.url,
          c.percentAlcohol,
          c.servings
        )
        FROM CocktailEntity c
        WHERE c.author.id = :authorId
      """)
  Page<CocktailViewModel>findAllByAuthor_Id(Long authorId, Pageable pageable);

  @Query("""
        SELECT new com.example.cocktails.model.dto.cocktail.CocktailViewModel(
          c.id,
          c.name,
          c.flavour,
          c.spirit,
          c.author.firstName,
          c.author.lastName,
          c.picture.url,
          c.percentAlcohol,
          c.servings
        )
        FROM CocktailEntity c
        JOIN c.favoriteUsers u
        WHERE u.id = :userId
      """)
  Page<CocktailViewModel> findFavoriteCocktailsByUserId(@Param("userId") Long userId, Pageable pageable);


  @Query("""
      SELECT new com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel(
        c.id,
        c.picture.url,
        c.author.firstName,
        c.author.lastName
      )
      FROM CocktailEntity c
      WHERE c.type = :type
      ORDER BY FUNCTION('RAND')
    """)
  List<CocktailHomePageViewModel> getRandomCocktailsByType(@Param("type") TypeNameEnum type, Pageable pageable);

  @Query("""
    SELECT new com.example.cocktails.model.dto.cocktail.CocktailDetailsViewModel(
        c.id,
        c.name,
        c.ingredients,
        c.preparation,
        c.flavour,
        c.author.firstName,
        c.author.lastName,
        c.videoUrl,
        c.spirit,
        c.picture.url,
        c.percentAlcohol,
        c.servings,
        new com.example.cocktails.model.dto.picture.PictureViewModel(c.picture.id, c.picture.url)
    )
    FROM CocktailEntity c
    WHERE c.id = :id
""")
  Optional<CocktailDetailsViewModel> findCocktailDetailsById(Long id);

  boolean existsByIdAndAuthor_Id(Long id, Long authorId);

  long countCocktailEntitiesBySpirit(SpiritNameEnum spirit);
}


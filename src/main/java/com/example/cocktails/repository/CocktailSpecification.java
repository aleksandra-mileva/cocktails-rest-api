package com.example.cocktails.repository;

import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.entity.CocktailEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CocktailSpecification implements Specification<CocktailEntity> {

  private final SearchCocktailDto searchCocktailDto;

  public CocktailSpecification(SearchCocktailDto searchCocktailDto) {
    this.searchCocktailDto = searchCocktailDto;
  }

  @Override
  public Predicate toPredicate(Root<CocktailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (searchCocktailDto.name() != null && !searchCocktailDto.name().isEmpty()) {
      String searchName = "%" + searchCocktailDto.name().toLowerCase() + "%";
      predicates.add(cb.like(cb.lower(root.get("name")), searchName));
    }

    if (searchCocktailDto.flavour() != null) {
      predicates.add(cb.equal(root.get("flavour"), searchCocktailDto.flavour()));
    }

    if (searchCocktailDto.spirit() != null) {
      predicates.add(cb.equal(root.get("spirit"), searchCocktailDto.spirit()));
    }

    if (searchCocktailDto.type() != null) {
      predicates.add(cb.equal(root.get("type"), searchCocktailDto.type()));
    }

    if (searchCocktailDto.minPercentAlcohol() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("percentAlcohol"), searchCocktailDto.minPercentAlcohol()));
    }

    if (searchCocktailDto.maxPercentAlcohol() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("percentAlcohol"), searchCocktailDto.maxPercentAlcohol()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));

  }
}

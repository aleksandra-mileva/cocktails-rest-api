package com.example.cocktails.repository;

import com.example.cocktails.model.dto.SearchCocktailDto;
import com.example.cocktails.model.entity.CocktailEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class CocktailSpecification implements Specification<CocktailEntity> {

  private final SearchCocktailDto searchCocktailDto;

  public CocktailSpecification(SearchCocktailDto searchCocktailDto) {
    this.searchCocktailDto = searchCocktailDto;
  }

  @Override
  public Predicate toPredicate(Root<CocktailEntity> root, CriteriaQuery<?> query,
      CriteriaBuilder cb) {

    Predicate p = cb.conjunction();

    if (searchCocktailDto.name() != null && !searchCocktailDto.name().isEmpty()) {
      String searchName = "%" + searchCocktailDto.name().toLowerCase() + "%";
      p.getExpressions().add(
          cb.and(cb.like(cb.lower(root.get("name")), searchName)));
    }

    if (searchCocktailDto.flavour() != null) {
      p.getExpressions().add(
          cb.equal(root.get("flavour"), searchCocktailDto.flavour()));
    }

    if (searchCocktailDto.spirit() != null) {
      p.getExpressions().add(
          cb.equal(root.get("spirit"), searchCocktailDto.spirit()));
    }

    if (searchCocktailDto.minPercentAlcohol() != null) {
      p.getExpressions().add(
          cb.and(cb.greaterThanOrEqualTo(root.get("percentAlcohol"),
              searchCocktailDto.minPercentAlcohol())));
    }

    if (searchCocktailDto.maxPercentAlcohol() != null) {
      p.getExpressions().add(
          cb.and(cb.lessThanOrEqualTo(root.get("percentAlcohol"),
              searchCocktailDto.maxPercentAlcohol())));
    }

    if (searchCocktailDto.type()!= null) {
      p.getExpressions().add(
          cb.equal(root.get("type"), searchCocktailDto.type()));
    }

    return p;
  }
}

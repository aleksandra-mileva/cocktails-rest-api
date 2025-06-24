package com.example.cocktails.repository;

import com.example.cocktails.model.entity.RoleEntity;
import com.example.cocktails.model.entity.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByRole(RoleNameEnum role);
}

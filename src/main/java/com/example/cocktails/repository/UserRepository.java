package com.example.cocktails.repository;

import com.example.cocktails.model.entity.UserEntity;
import com.example.cocktails.model.entity.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<UserEntity> findByUsername(String username);

  @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.role = :role")
  List<UserEntity> findByRole(@Param("role") RoleNameEnum role);

  Optional<UserEntity> findByEmail(String email);

  List<UserEntity> findAllByAccountVerifiedEqualsAndCreatedOnBefore(boolean accountVerified,
      Timestamp createdOn);

  // Alternative for isCocktailInUserFavorites:
//  @Query("""
//      SELECT CASE when COUNT(f)>0 then true else false end
//      from UserEntity u
//      join u.favorites f
//      where u.username = :username and f.id = :cocktailId
//      """)
//  boolean isCocktailInUserFavorites(String username, Long cocktailId);

  @Query("""
    SELECT EXISTS (
        SELECT 1
        FROM UserEntity u
        JOIN u.favorites f
        WHERE u.id = :id AND f.id = :cocktailId
    )
""")
  boolean isCocktailInUserFavorites(Long id, Long cocktailId);

  @Query("""
    SELECT EXISTS (
        SELECT 1 FROM UserEntity u
        JOIN u.roles r
        WHERE u.id = :id AND r.role = :role
    )
""")
  boolean existsByUserIdAndRole(Long id, RoleNameEnum role);
}

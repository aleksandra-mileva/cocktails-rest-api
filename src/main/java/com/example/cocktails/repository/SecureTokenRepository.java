package com.example.cocktails.repository;

import com.example.cocktails.model.entity.SecureTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecureTokenRepository extends JpaRepository<SecureTokenEntity, Long> {

  Optional<SecureTokenEntity> findByToken(String token);

  List<SecureTokenEntity> findAllByExpireAtBefore(LocalDateTime expireAt);
}

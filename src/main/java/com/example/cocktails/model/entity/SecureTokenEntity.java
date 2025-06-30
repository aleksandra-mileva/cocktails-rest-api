package com.example.cocktails.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "secure_tokens")
public class SecureTokenEntity extends BaseEntity {

  private String token;

  @CreationTimestamp
  private Timestamp timestamp;

  private LocalDateTime expireAt;

  @ManyToOne
  private UserEntity user;

  public boolean isExpired() {
    return expireAt.isBefore(LocalDateTime.now());
  }

  public String getToken() {
    return token;
  }

  public SecureTokenEntity setToken(String token) {
    this.token = token;
    return this;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public SecureTokenEntity setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public LocalDateTime getExpireAt() {
    return expireAt;
  }

  public SecureTokenEntity setExpireAt(LocalDateTime expireAt) {
    this.expireAt = expireAt;
    return this;
  }

  public UserEntity getUser() {
    return user;
  }

  public SecureTokenEntity setUser(UserEntity user) {
    this.user = user;
    return this;
  }
}

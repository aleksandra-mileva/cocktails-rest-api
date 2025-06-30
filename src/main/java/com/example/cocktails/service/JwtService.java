package com.example.cocktails.service;

import com.example.cocktails.model.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

  private final String jwtSecret;
  private final long expiration;

  public JwtService(@Value("${jwt.secret}") String jwtSecret,
      @Value("${jwt.expiration}") long expiration) {
    this.jwtSecret = jwtSecret;
    this.expiration = expiration;
  }

  public String generateToken(CustomUserDetails userPrincipal) {
    var now = new Date();
    List<String> roles = userPrincipal.getAuthorities()
        .stream().map(GrantedAuthority::getAuthority)
        .toList();

    return Jwts
        .builder()
        .setSubject(userPrincipal.getUsername())
        .claim("id", userPrincipal.getId())
        .claim("roles", roles)
        .setIssuedAt(now)
        .setNotBefore(now)
        .setExpiration(new Date(now.getTime() + expiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException
             | UnsupportedJwtException
             | MalformedJwtException
             | SignatureException
             | IllegalArgumentException ex) {
      throw new JwtException(ex.getMessage());
    }
  }

  private Key getSigningKey() {
    byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

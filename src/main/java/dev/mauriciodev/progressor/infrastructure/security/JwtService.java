package dev.mauriciodev.progressor.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public final class JwtService {

  private final SecretKey secretKey;
  private final long expirationMs;

  public JwtService(@Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration-ms}") long expirationMs) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMs = expirationMs;
  }

  public String generateToken(UserDetails userDetails) {
    String role = userDetails.getAuthorities().stream().findFirst()
        .map(a -> a.getAuthority().replace("ROLE_", "")).orElse("STUDENT");

    return Jwts.builder().subject(userDetails.getUsername()).claim("role", role)
        .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(secretKey).compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        .getPayload();
    return claimsResolver.apply(claims);
  }
}
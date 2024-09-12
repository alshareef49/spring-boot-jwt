package com.alshareef.security.utils;

import com.alshareef.security.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${expire.token.time}")
    private long expireTokenTime;

    public JwtUtil() {
    }

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .findFirst()  // Assumes the user has one role; adapt if multiple roles are allowed
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return this.createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Instant currentInstant = Instant.now();
        Date issueDate = Date.from(currentInstant);
        Date expirationDate = Date.from(currentInstant.plusSeconds(this.expireTokenTime));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT").compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    public String extractUsername(String token) {
        Claims claims = (Claims)Jwts
                .parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String extractRole(String token) {
        Claims claims = (Claims)Jwts
                .parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build().parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    private Boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return ((Claims)Jwts.parserBuilder().setSigningKey(this.getSigningKey()).build().parseClaimsJws(token).getBody()).getExpiration();
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

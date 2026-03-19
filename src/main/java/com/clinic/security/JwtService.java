package com.clinic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class JwtService {

    @Value("${app.jwt.secret:change-this-secret-key-with-at-least-32-characters}")
    private String secret;

    @Value("${app.jwt.expiration-ms:86400000}")
    private long expirationMs;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            // Automatically pads or errors if the key is too short for HS256
            if (keyBytes.length < 32) {
                log.warn("JWT Secret is too short. Using padding to reach 256-bit requirement.");
                byte[] paddedKey = new byte[32];
                System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
                this.signingKey = Keys.hmacShaKeyFor(paddedKey);
            } else {
                this.signingKey = Keys.hmacShaKeyFor(keyBytes);
            }
        } catch (Exception e) {
            log.error("Critical error initializing JWT Signing Key", e);
            throw e; 
        }
    }

    public String generateToken(AdminPrincipal principal) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(principal.getUsername())
                .id(UUID.randomUUID().toString())
                .claims(Map.of(
                        "doctorId", principal.getDoctorId(),
                        "role", principal.getAuthorities().iterator().next().getAuthority()
                ))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey) // No Algorithm needed here in 0.12.x
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTokenId(String token) {
        return extractAllClaims(token).getId();
    }

    public LocalDateTime extractExpiration(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

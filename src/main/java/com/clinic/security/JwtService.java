package com.clinic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {
    private static final String DEFAULT_JWT_SECRET = "change-this-secret-key-with-at-least-32-characters";
    private static final long DEFAULT_EXPIRATION_MS = 86_400_000L;

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") String expirationMsValue
    ) {
        String normalizedSecret = normalizeSecret(secret);
        byte[] keyBytes = normalizedSecret.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(padKeyIfNeeded(keyBytes));
        this.expirationMs = parseExpiration(expirationMsValue);
    }

    public String generateToken(AdminPrincipal principal) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiry = new Date(now + expirationMs);
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setId(UUID.randomUUID().toString())
                .addClaims(Map.of(
                        "doctorId", principal.getDoctorId(),
                        "role", principal.getAuthorities().iterator().next().getAuthority()
                ))
                .setIssuedAt(issuedAt)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
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
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private byte[] padKeyIfNeeded(byte[] keyBytes) {
        if (keyBytes.length >= 32) {
            return keyBytes;
        }
        byte[] padded = new byte[32];
        System.arraycopy(keyBytes, 0, padded, 0, keyBytes.length);
        for (int i = keyBytes.length; i < padded.length; i++) {
            padded[i] = (byte) (i + 1);
        }
        return padded;
    }

    private String normalizeSecret(String secret) {
        if (secret == null || secret.trim().isEmpty()) {
            log.warn("JWT secret is blank. Falling back to default secret value.");
            return DEFAULT_JWT_SECRET;
        }
        return secret.trim();
    }

    private long parseExpiration(String expirationMsValue) {
        if (expirationMsValue == null || expirationMsValue.trim().isEmpty()) {
            log.warn("JWT expiration is blank. Falling back to default {} ms.", DEFAULT_EXPIRATION_MS);
            return DEFAULT_EXPIRATION_MS;
        }
        try {
            return Long.parseLong(expirationMsValue.trim());
        } catch (NumberFormatException ex) {
            log.warn("Invalid JWT expiration '{}'. Falling back to default {} ms.", expirationMsValue, DEFAULT_EXPIRATION_MS);
            return DEFAULT_EXPIRATION_MS;
        }
    }
}

package com.clinic.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {
    private static final String DEFAULT_JWT_SECRET = "change-this-secret-key-with-at-least-32-characters";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final Base64.Encoder URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();

    private final ObjectMapper objectMapper;
    private final byte[] secretBytes;
    private final long expirationMs;

    public JwtService(
            ObjectMapper objectMapper,
            @Value("${app.jwt.secret:change-this-secret-key-with-at-least-32-characters}") String secret,
            @Value("${app.jwt.expiration-ms:86400000}") String expirationMsValue
    ) {
        this.objectMapper = objectMapper;
        this.secretBytes = (secret == null || secret.isBlank() ? DEFAULT_JWT_SECRET : secret.trim()).getBytes(StandardCharsets.UTF_8);
        this.expirationMs = parseExpiration(expirationMsValue);
    }

    public String generateToken(AdminPrincipal principal) {
        long nowMs = System.currentTimeMillis();
        long expMs = nowMs + expirationMs;

        Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", principal.getUsername());
        payload.put("jti", UUID.randomUUID().toString());
        payload.put("iat", nowMs / 1000);
        payload.put("exp", expMs / 1000);
        payload.put("doctorId", principal.getDoctorId());
        payload.put("role", principal.getAuthorities().iterator().next().getAuthority());

        String encodedHeader = encodeJson(header);
        String encodedPayload = encodeJson(payload);
        String signingInput = encodedHeader + "." + encodedPayload;
        String signature = sign(signingInput);
        return signingInput + "." + signature;
    }

    public String extractUsername(String token) {
        return asString(parseAndValidatePayload(token).get("sub"));
    }

    public String extractTokenId(String token) {
        return asString(parseAndValidatePayload(token).get("jti"));
    }

    public LocalDateTime extractExpiration(String token) {
        long expEpochSec = asLong(parseAndValidatePayload(token).get("exp"));
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(expEpochSec), ZoneId.systemDefault());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Map<String, Object> payload = parseAndValidatePayload(token);
            String username = asString(payload.get("sub"));
            long exp = asLong(payload.get("exp"));
            return username.equals(userDetails.getUsername()) && exp > Instant.now().getEpochSecond();
        } catch (Exception ex) {
            return false;
        }
    }

    private Map<String, Object> parseAndValidatePayload(String token) {
        if (token == null || token.isBlank()) throw new IllegalArgumentException("JWT token is blank");
        String[] parts = token.split("\\.");
        if (parts.length != 3) throw new IllegalArgumentException("JWT token format is invalid");

        String signingInput = parts[0] + "." + parts[1];
        String expectedSignature = sign(signingInput);
        if (!MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8), parts[2].getBytes(StandardCharsets.UTF_8))) {
            throw new IllegalArgumentException("JWT signature is invalid");
        }

        try {
            return objectMapper.readValue(URL_DECODER.decode(parts[1]), new TypeReference<>() {});
        } catch (Exception ex) {
            throw new IllegalArgumentException("JWT payload is invalid", ex);
        }
    }

    private String encodeJson(Map<String, Object> value) {
        try {
            return URL_ENCODER.encodeToString(objectMapper.writeValueAsBytes(value));
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to serialize JWT section", ex);
        }
    }

    private String sign(String input) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secretBytes, HMAC_ALGORITHM));
            return URL_ENCODER.encodeToString(mac.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to sign JWT", ex);
        }
    }

    private String asString(Object value) {
        if (value == null) throw new IllegalArgumentException("Missing JWT claim");
        return String.valueOf(value);
    }

    private long asLong(Object value) {
        if (value instanceof Number number) return number.longValue();
        if (value instanceof String s) return Long.parseLong(s);
        throw new IllegalArgumentException("Invalid JWT numeric claim");
    }

    private long parseExpiration(String expirationMsValue) {
        if (expirationMsValue == null || expirationMsValue.trim().isEmpty()) {
            return 86400000L;
        }
        try {
            return Long.parseLong(expirationMsValue.trim());
        } catch (NumberFormatException ex) {
            log.warn("Invalid JWT expiration '{}', falling back to default 86400000", expirationMsValue);
            return 86400000L;
        }
    }
}

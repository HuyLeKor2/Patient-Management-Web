package com.pm.auth_service.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final Key secretkey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        logger.info("JWT Secret length: {} characters", secret.length());
        logger.info("JWT Secret (first 10 chars): {}...", secret.substring(0, Math.min(10, secret.length())));
        
        // Directly use the secret string bytes for HMAC-SHA key generation
        // The Keys.hmacShaKeyFor() method requires at least 256 bits (32 characters)
        this.secretkey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretkey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretkey)
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature", e);
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT", e);
        }
    }
}
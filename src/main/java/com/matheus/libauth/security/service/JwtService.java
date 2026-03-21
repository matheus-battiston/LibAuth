package com.matheus.libauth.security.service;

import com.matheus.libauth.security.config.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtService {

    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String NOME = "nome";

    private final SecretKey key;

    public JwtService(AuthProperties authProperties) {
        String secret = authProperties.getJwt().getSecret();
        if (secret == null || secret.isBlank()) throw new IllegalStateException("auth.jwt.secret nao foi configurado");
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token) {
        return getClaims(token).get(USER_ID, Long.class);
    }

    public String getUserEmail(String token) {
        return getClaims(token).get(EMAIL, String.class);
    }

    public String getUserNome(String token) {
        return getClaims(token).get(NOME, String.class);
    }
}
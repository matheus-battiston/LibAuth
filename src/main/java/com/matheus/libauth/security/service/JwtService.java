package com.matheus.libauth.security.service;

import com.matheus.libauth.security.config.AuthProperties;
import com.matheus.libauth.security.util.KeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.PublicKey;

public class JwtService {

    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String NOME = "nome";

    private final PublicKey key;

    public JwtService(AuthProperties authProperties) {
        String publicKey = authProperties.getJwt().getPublicKey();
        if (publicKey == null || publicKey.isBlank()) {
            throw new IllegalStateException("auth.jwt.public-key nao foi configurado");
        }
        this.key = KeyUtils.getPublicKey(publicKey);
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
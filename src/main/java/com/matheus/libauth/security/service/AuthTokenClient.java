package com.matheus.libauth.security.service;

import com.matheus.libauth.security.config.AuthProperties;
import com.matheus.libauth.security.controller.request.ExchangeCodeRequest;
import com.matheus.libauth.security.controller.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthTokenClient {
    private static final String AUTH_TOKEN_URL = "/oauth2/token";
    private static final String ERRO = "Erro ao trocar code por token";

    private final RestTemplate restTemplate;
    private final AuthProperties authProperties;

    public String exchangeCodeForToken(String code) {

        String url = authProperties.getAuthUrl() + AUTH_TOKEN_URL;

        TokenResponse response = restTemplate.postForObject(
                url,
                new ExchangeCodeRequest(code),
                TokenResponse.class
        );

        if (response == null || response.getAccessToken() == null) throw new RuntimeException(ERRO);
        return response.getAccessToken();
    }
}
package com.matheus.libauth.security.controller;

import com.matheus.libauth.security.config.AuthProperties;
import com.matheus.libauth.security.service.AppCookieService;
import com.matheus.libauth.security.service.AuthTokenClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OAuth2CallbackController {

    private final AuthTokenClient authTokenClient;
    private final AppCookieService appCookieService;
    private final AuthProperties authProperties;

    @GetMapping("/oauth2/callback")
    public void callback(
            @RequestParam("code") String code,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String token = authTokenClient.exchangeCodeForToken(code);

        ResponseCookie cookie = appCookieService.buildTokenCookie(
                authProperties.getCallback().getCookieName(),
                token,
                request,
                authProperties.getCallback().getMaxAge()
        );

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.sendRedirect(authProperties.getCallback().getRedirectUrl());
    }
}
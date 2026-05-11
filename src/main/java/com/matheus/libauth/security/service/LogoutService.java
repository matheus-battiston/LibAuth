package com.matheus.libauth.security.service;

import com.matheus.libauth.security.config.AuthProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final AppCookieService appCookieService;
    private final AuthProperties authProperties;

    public ResponseEntity<Void> logout(HttpServletRequest request) {
        ResponseCookie cookie = appCookieService.buildTokenCookie(
                authProperties.getCallback().getCookieName(),
                "",
                request,
                0
        );

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}

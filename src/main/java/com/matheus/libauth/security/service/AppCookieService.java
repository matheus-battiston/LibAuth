package com.matheus.libauth.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class AppCookieService {

    private static final String LOCALHOST = "localhost";
    private static final String PATH = "/";
    private static final String SAME_SITE = "Lax";

    public ResponseCookie buildTokenCookie(
            String cookieName,
            String token,
            HttpServletRequest request,
            long maxAge
    ) {
        boolean isLocal = request.getServerName().contains(LOCALHOST);

        return ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(!isLocal)
                .path(PATH)
                .sameSite(SAME_SITE)
                .maxAge(maxAge)
                .build();
    }

}
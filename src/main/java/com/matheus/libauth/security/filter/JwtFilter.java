package com.matheus.libauth.security.filter;

import com.matheus.libauth.security.config.AuthProperties;
import com.matheus.libauth.security.dto.UsuarioAutenticado;
import com.matheus.libauth.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String EXPIRADO = "Token expirado";
    private static final String INVALIDO = "Token invalido";

    private final JwtService jwtService;
    private final AuthProperties authProperties;

    public JwtFilter(JwtService jwtService, AuthProperties authProperties) {
        this.jwtService = jwtService;
        this.authProperties = authProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Long userId = jwtService.getUserId(token);
                String email = jwtService.getUserEmail(token);
                String nome = jwtService.getUserNome(token);

                UsuarioAutenticado usuario = new UsuarioAutenticado(nome, email, userId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                SecurityContextHolder.clearContext();
                sendUnauthorized(response, EXPIRADO);
                return;
            } catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
                sendUnauthorized(response, INVALIDO);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String configuredCookieName = authProperties.getCallback().getCookieName();

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                String name = cookie.getName();

                boolean isConfiguredCookie = configuredCookieName != null
                        && configuredCookieName.equals(name);

                if (isConfiguredCookie) {
                    String value = cookie.getValue();
                    if (value != null && !value.isBlank()) return value;
                }
            }
        }

        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX))
            return authHeader.substring(BEARER_PREFIX.length());

        return null;
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
        response.getWriter().flush();
    }
}
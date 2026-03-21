package com.matheus.libauth.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private boolean enabled = true;

    private Jwt jwt = new Jwt();
    private List<String> publicPaths = new ArrayList<>();

    @Setter
    @Getter
    public static class Jwt {
        private String secret;
        private long expiration;

    }
}
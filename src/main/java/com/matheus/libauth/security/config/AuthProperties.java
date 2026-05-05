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

    private String authUrl;

    private Jwt jwt = new Jwt();

    private Callback callback = new Callback();

    private List<String> publicPaths = new ArrayList<>();

    @Setter
    @Getter
    public static class Jwt {
        private String publicKey;
        private long expiration = 86400000;
    }

    @Setter
    @Getter
    public static class Callback {
        private String cookieName = "token";
        private String redirectUrl = "/";
        private long maxAge = 86400;
    }
}
package com.matheus.libauth.security.config;


import com.matheus.libauth.security.controller.InfoUsuarioController;
import com.matheus.libauth.security.filter.JwtFilter;
import com.matheus.libauth.security.service.InfoUsuarioService;
import com.matheus.libauth.security.service.JwtService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AutoConfiguration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnProperty(prefix = "auth", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuthAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InfoUsuarioService infoUsuarioService() {
        return new InfoUsuarioService();
    }

    @Bean
    @ConditionalOnMissingBean
    public InfoUsuarioController infoUsuarioController(InfoUsuarioService infoUsuarioService) {
        return new InfoUsuarioController(infoUsuarioService);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtService jwtService(AuthProperties authProperties) {
        return new JwtService(authProperties);
    }
    @Bean
    @ConditionalOnMissingBean
    public JwtFilter jwtFilter(JwtService jwtService) {
        return new JwtFilter(jwtService);
    }
    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter,
            AuthProperties authProperties
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> {
            if (!authProperties.getPublicPaths().isEmpty()) {
                auth.requestMatchers(authProperties.getPublicPaths().toArray(new String[0])).permitAll();
            }
            auth.anyRequest().authenticated();
        });

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
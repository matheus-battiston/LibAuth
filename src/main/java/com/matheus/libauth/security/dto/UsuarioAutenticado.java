package com.matheus.libauth.security.dto;

import lombok.*;
import org.springframework.security.core.AuthenticatedPrincipal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAutenticado implements AuthenticatedPrincipal {
    String nome;
    String email;
    Long id;

    @Override
    public @NonNull String getName() {
        return id.toString();
    }
}

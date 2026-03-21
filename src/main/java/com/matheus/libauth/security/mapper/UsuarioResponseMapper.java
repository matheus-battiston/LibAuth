package com.matheus.libauth.security.mapper;


import com.matheus.libauth.security.controller.response.UsuarioResponse;
import com.matheus.libauth.security.dto.UsuarioAutenticado;

public class UsuarioResponseMapper {
    public static UsuarioResponse toResponse(UsuarioAutenticado usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }
}

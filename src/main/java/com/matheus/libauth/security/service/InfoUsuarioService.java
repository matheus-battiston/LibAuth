package com.matheus.libauth.security.service;

import com.matheus.libauth.security.controller.response.UsuarioResponse;
import com.matheus.libauth.security.dto.UsuarioAutenticado;
import com.matheus.libauth.security.mapper.UsuarioResponseMapper;
import org.springframework.security.core.Authentication;

public class InfoUsuarioService {

    public UsuarioResponse getUsuarioAutenticado(Authentication authentication) {
        UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) authentication.getPrincipal();
        return UsuarioResponseMapper.toResponse(usuarioAutenticado);
    }
}
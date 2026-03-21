package com.matheus.libauth.security.controller;

import com.matheus.libauth.security.controller.response.UsuarioResponse;
import com.matheus.libauth.security.service.InfoUsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoUsuarioController {

    private final InfoUsuarioService infoUsuarioService;

    public InfoUsuarioController(InfoUsuarioService infoUsuarioService) {
        this.infoUsuarioService = infoUsuarioService;
    }

    @GetMapping("/me")
    public UsuarioResponse testAuth(Authentication authentication) {
        return infoUsuarioService.getUsuarioAutenticado(authentication);
    }
}
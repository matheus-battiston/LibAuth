package com.matheus.libauth.security.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioRequest {

    @NotBlank(message = "Nome deve ser preenchido")
    @Size(min = 3, max = 255, message = "Numero de caracteres do nome deve ser no minimo 3 e maximo 255")
    private String nome;

    @NotNull(message = "Email deve ser preenchido")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Deve ser definido uma senha")
    private String senha;

}

package com.matheus.libauth.security.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExchangeCodeRequest {
    private String code;
}
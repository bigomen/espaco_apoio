package com.spaco_apoio.api.rest;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResetPassword {

    @NotEmpty(message = "Email")
    private String email;

    @NotEmpty(message = "Token")
    private String token;

    @NotEmpty(message = "Senha")
    private String password;
}

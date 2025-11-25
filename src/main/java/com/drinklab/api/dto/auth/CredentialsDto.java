package com.drinklab.api.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDto {

    @NotBlank
    private String email;

    @NotNull
    private String password;

}

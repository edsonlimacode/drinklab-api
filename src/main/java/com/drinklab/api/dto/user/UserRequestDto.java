package com.drinklab.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Long groupId;
}

package com.drinklab.api.dto.token;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenDto {

    private String userName;
    private String token;
    private String refreshToken;
}

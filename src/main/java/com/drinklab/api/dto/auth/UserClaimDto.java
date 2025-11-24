package com.drinklab.api.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserClaimDto {

    private Long id;
    private String userName;

}

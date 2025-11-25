package com.drinklab.api.controller;


import com.drinklab.api.dto.auth.CredentialsDto;
import com.drinklab.api.dto.token.JwtTokenDto;
import com.drinklab.domain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public JwtTokenDto signIn(@RequestBody CredentialsDto credentialsDto) {
        return this.authService.signIn(credentialsDto);
    }

}

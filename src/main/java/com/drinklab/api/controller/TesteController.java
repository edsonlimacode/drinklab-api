package com.drinklab.api.controller;


import com.drinklab.api.dto.token.JwtTokenDto;
import com.drinklab.core.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private JwtTokenProvider jwtProvider;

    @GetMapping
    public JwtTokenDto teste(){

        List<String> roleAdmin = List.of("ROLE_ADMIN", "ROLE_USER");

        String token = jwtProvider.generateAccessToken("edson", roleAdmin, 86400000L);

        return new JwtTokenDto("edson", token,"DKEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKDEOKD");

    }

}

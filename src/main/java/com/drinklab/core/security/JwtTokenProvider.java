package com.drinklab.core.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    public String generateAccessToken(String userName, List<String> authorities, Long expiration) {

        Date now = new Date();

        return JWT.create()
                .withClaim("authorities", authorities)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + expiration))
                .withSubject(userName)
                .sign(Algorithm.HMAC256(jwtConfigProperties.getSecret().getBytes()));
    }
}

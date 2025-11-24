package com.drinklab.core.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.drinklab.api.dto.auth.UserClaimDto;
import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Autowired
    private UserService userService;

    public String generateAccessToken(UserClaimDto userClaimDto, List<String> authorities) {

        Date now = new Date();

        return JWT.create()
                .withClaim("user_id", userClaimDto.getId())
                .withClaim("authorities", authorities)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + jwtConfigProperties.getExpiration()))
                .withSubject(userClaimDto.getUserName())
                .sign(getAlgorithm());
    }

    public Authentication getAuthentication(String token) {

        DecodedJWT decodedJWT = this.decodedJWT(token);

        UserDetails userDetails = this.userService.loadUserByUsername(decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    public boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = this.decodedJWT(token);

        return !decodedJWT.getExpiresAt().before(new Date());
    }

    public DecodedJWT decodedJWT(String token) {

        try {
            Algorithm algorithm = getAlgorithm();

            JWTVerifier verifier = JWT.require(algorithm).build();

            return verifier.verify(token);
        } catch (Exception e) {
            throw new BadRequestException("token iválido");
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtConfigProperties.getSecret().getBytes());
    }
}

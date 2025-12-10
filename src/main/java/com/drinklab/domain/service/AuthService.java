package com.drinklab.domain.service;

import com.drinklab.api.dto.auth.CredentialsDto;
import com.drinklab.api.dto.auth.UserClaimDto;
import com.drinklab.api.dto.token.JwtTokenDto;
import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.UnauthorizedException;
import com.drinklab.core.security.JwtTokenProvider;
import com.drinklab.domain.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtTokenDto signIn(@RequestBody CredentialsDto credentialsDto) {

        try {
            Authentication authenticate = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentialsDto.getEmail(), credentialsDto.getPassword()));

            List<String> authorities = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            UserEntity user = this.userService.findByEmail(authenticate.getName());

            if (!user.getActive()) {
                throw new UnauthorizedException("Você não tem autorização para acessar o sistema no momento, entre em contato com os administradores");
            }

            UserClaimDto userClaimDto = new UserClaimDto(user.getId(), user.getEmail());

            String token = jwtProvider.generateAccessToken(userClaimDto, authorities);

            return new JwtTokenDto(credentialsDto.getEmail(), token, "");

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new BadRequestException("E-mail ou Senha inválidos");
        }

    }
}

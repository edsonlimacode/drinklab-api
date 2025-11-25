package com.drinklab.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtDoFilter extends GenericFilterBean {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            String bearerToken = request.getHeader("Authorization");

            if (bearerToken != null && jwtTokenProvider.isTokenExpired(bearerToken.substring("Bearer ".length()))) {

                String token = bearerToken.substring("Bearer ".length());

                Authentication authentication = this.jwtTokenProvider.getAuthentication(token);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {

            HttpServletResponse response = (HttpServletResponse) servletResponse;

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            servletResponse.getWriter().write(mapper.writeValueAsString("Token Inválido"));
        }


    }
}

package com.drinklab.core.security;

import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Autowired
    private UserService userService;

   private Authentication getAuthentication(){
       return SecurityContextHolder.getContext().getAuthentication();
   }

   public Long getUserId(){
       User principal = (User) this.getAuthentication().getPrincipal();

       UserEntity userEntity = this.userService.findByEmail(principal.getUsername());

       return  userEntity.getId();
   }

}

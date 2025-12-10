package com.drinklab.core.security;

import com.drinklab.core.properties.GroupProperties;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.repository.DistributorRepository;
import com.drinklab.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtUtils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private GroupProperties groupProperties;

   private Authentication getAuthentication(){
       return SecurityContextHolder.getContext().getAuthentication();
   }

   public Long getUserLoggedId(){
       User principal = (User) this.getAuthentication().getPrincipal();

       Optional<UserEntity> user = this.userRepository.findByEmail(principal.getUsername());

       return user.map(UserEntity::getId).orElse(null);

   }

    public boolean isMaster() {
        return this.getAuthentication().getAuthorities().stream().anyMatch(
                u -> u.getAuthority().equals(groupProperties.getMaster()));
    }

}

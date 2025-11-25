package com.drinklab.domain.service;


import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    public void create(UserEntity user) {

        this.groupService.findById(user.getGroup().getId());

        var userExists = this.getUserByEmail(user.getEmail());

        if (userExists) {
            throw new BadRequestException("Já existe um usuário cadastrado com o email: " + user.getEmail());
        }

        this.userRepository.save(user);
    }

    @Transactional
    public UserEntity update(Long id, UserEntity user) {

        user.setId(id);

        this.findById(id);

        return user;
//        return this.userRepository.save(user);

    }

    public boolean getUserByEmail(String email) {
        return userRepository.userExistsByEmail(email);
    }

    public UserEntity findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("Recurso com email %s, não foi encontrado", email)));
    }

    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Recurso com id %d, não foi encontrado", id)));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = this.findByEmail(email);

        return new User(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getGroup().getName())));
    }
}

package com.drinklab.domain.service;


import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.domain.model.User;
import com.drinklab.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(User user) {

        this.groupService.findById(user.getGroup().getId());

        var userExists = this.getUserByEmail(user.getEmail());

        if (userExists) {
            throw new BadRequestException("Já existe um usuário cadastrado com o email: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        this.userRepository.save(user);
    }

    public boolean getUserByEmail(String email) {
        return userRepository.userExistsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}

package com.drinklab.domain.service;


import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterUsersService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void create(UserEntity user) {

        checkUserByEmail(user.getEmail());

        this.userRepository.save(user);

    }

    public UserEntity update(UserEntity user) {
        return this.userRepository.save(user);
    }

    public UserEntity findById(Long id) {
        return this.userRepository.findById(id) .orElseThrow(() ->
                new NotFoundException(String.format("Usuário com id %d, não foi encontrado", id)));
    }

    public List<UserEntity> findAll() {
       return this.userRepository.findAll();
    }

    @Transactional
    public void active(Long id){
        UserEntity user = this.findById(id);
        user.setActive(true);
    }

    @Transactional
    public void inactive(Long id){
        UserEntity user = this.findById(id);
        user.setActive(false);
    }

    public boolean checkUserByEmailExists(String email) {
        return userRepository.userExistsByEmail(email);
    }

    private void checkUserByEmail(String email) {
        var userExists = this.checkUserByEmailExists(email);

        if (userExists) {
            throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
        }
    }
}

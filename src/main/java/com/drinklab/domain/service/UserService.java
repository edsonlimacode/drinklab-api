package com.drinklab.domain.service;

import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.ForbiddenException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.core.properties.GroupProperties;
import com.drinklab.core.security.JwtUtils;
import com.drinklab.domain.model.Distributor;
import com.drinklab.domain.model.Group;
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
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private GroupProperties groupProperties;

    @Transactional
    public void create(UserEntity user) {

        checkUserByEmail(user.getEmail());

        if (jwtUtils.isMaster()) {
            this.userRepository.save(user);
        } else {

            Distributor distributor = this.distributorService.getDistributorByUserId(jwtUtils.getUserLoggedId());

            Group group = this.groupService.findById(user.getGroup().getId());

            if (group.getName().equals(groupProperties.getMaster())) {
                throw new ForbiddenException(
                        String.format("Você não pode adicionar o grupo: %s, a um usuário", group.getName()));
            }

            UserEntity userSaved = this.userRepository.save(user);

            distributor.getUsers().add(userSaved);
        }

    }

    public UserEntity update(UserEntity user) {

        String groupName = user.getGroup().getName();

        if (groupName.equals(groupProperties.getMaster())) {
            throw new ForbiddenException(
                    String.format("Você não tem permissão para adicionar o grupo: %s, a um usuário", groupName));
        }

        return this.userRepository.save(user);
    }

    public UserEntity findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(String.format("Usuário com email %s, não foi encontrado", email)));
    }

    public UserEntity findById(Long id) {
        if (jwtUtils.isMaster()) {
            return this.userRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Usuário com id %d, não foi encontrado", id)));
        }

        Optional<UserEntity> user = this.findAll().stream()
                .filter(u -> u.getId().equals(id)).findFirst();

        if (user.isEmpty()) {
            throw new BadRequestException("Este usuário não pertence a sua distribuidora");
        }

        return user.get();
    }

    public List<UserEntity> findAll() {

        if (jwtUtils.isMaster()) {
            return this.userRepository.findAll();
        }

        Distributor distributor = distributorService.getDistributorByUserId(jwtUtils.getUserLoggedId());

        return distributor.getUsers().stream().toList();
    }

    @Transactional
    public void active(Long id) {
        UserEntity user = this.findById(id);
        user.setActive(true);
    }

    @Transactional
    public void inactive(Long id) {
        UserEntity user = this.findById(id);
        user.setActive(false);
    }

    private void checkUserByEmail(String email) {
        var userExists = userRepository.userExistsByEmail(email);

        if (userExists) {
            throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = this.findByEmail(email);

        return new User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getGroup().getName())));
    }

}

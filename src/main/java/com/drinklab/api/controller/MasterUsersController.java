package com.drinklab.api.controller;


import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserRequestUpdateDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.api.mapper.UserMapper;
import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.service.MasterUsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/users")
public class MasterUsersController {

    @Autowired
    private MasterUsersService masterUsersService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CheckAuthority.Master
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserRequestDto userRequestDto) {

        UserEntity user = this.userMapper.toEntity(userRequestDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        this.masterUsersService.create(user);
    }

    @CheckAuthority.Master
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAll() {

        List<UserEntity> users = this.masterUsersService.findAll();

        List<UserResponseDto> userListDto = this.userMapper.toListDto(users);

        return ResponseEntity.ok(userListDto);

    }

    @CheckAuthority.Master
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestUpdateDto userRequestUpdateDto) {

        UserEntity user = this.masterUsersService.findById(id);

        UserEntity userEntity = this.userMapper.copyUserProperties(userRequestUpdateDto, user);

        UserEntity userUpdated = this.masterUsersService.update(userEntity);

        UserResponseDto userResponseDto = this.userMapper.toDto(userUpdated);

        return ResponseEntity.ok(userResponseDto);

    }

    @CheckAuthority.Master
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){

        UserEntity user = this.masterUsersService.findById(id);

        UserResponseDto userResponseDto = this.userMapper.toDto(user);

        return ResponseEntity.ok().body(userResponseDto);

    }

    @CheckAuthority.Master
    @DeleteMapping("/inactive/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactive(@PathVariable Long id){
        this.masterUsersService.inactive(id);
    }

    @CheckAuthority.Master
    @PutMapping("/active/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void active(@PathVariable Long id){
        this.masterUsersService.active(id);
    }
}

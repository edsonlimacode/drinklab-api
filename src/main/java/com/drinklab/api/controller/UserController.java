package com.drinklab.api.controller;


import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserRequestUpdateDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.api.mapper.UserMapper;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserRequestDto userRequestDto) {

        UserEntity user = this.userMapper.toEntity(userRequestDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        this.userService.create(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAll() {

        List<UserEntity> users = this.userService.findAll();

        List<UserResponseDto> userListDto = this.userMapper.toListDto(users);

        return ResponseEntity.ok(userListDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestUpdateDto userRequestUpdateDto) {

        UserEntity user = this.userService.findById(id);

        this.userMapper.copyUserProperties(userRequestUpdateDto, user);

        UserEntity userUpdated = this.userService.update(id, user);

        UserResponseDto userResponseDto = this.userMapper.toDto(userUpdated);

        return ResponseEntity.ok(userResponseDto);

    }
}

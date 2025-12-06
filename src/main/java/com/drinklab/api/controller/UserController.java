package com.drinklab.api.controller;


import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserRequestUpdateDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.api.mapper.UserMapper;
import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.model.Group;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.service.GroupService;
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
    private GroupService groupService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CheckAuthority.Admin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserRequestDto userRequestDto) {

        UserEntity user = this.userMapper.toEntity(userRequestDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        this.userService.create(user);
    }

    @CheckAuthority.Admin
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAll() {

        List<UserEntity> users = this.userService.findAll();

        List<UserResponseDto> userListDto = this.userMapper.toListDto(users);

        return ResponseEntity.ok(userListDto);

    }

    @CheckAuthority.Admin
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestUpdateDto userRequestUpdateDto) {

        UserEntity user = this.userService.findById(id);

        if(userRequestUpdateDto.getGroupId() != null){
            Group group = this.groupService.findById(userRequestUpdateDto.getGroupId());
            user.setGroup(group);
        }

        UserEntity userEntity = this.userMapper.copyUserProperties(userRequestUpdateDto, user);

        UserEntity userUpdated = this.userService.update(userEntity);

        UserResponseDto userResponseDto = this.userMapper.toDto(userUpdated);

        return ResponseEntity.ok(userResponseDto);

    }

    @CheckAuthority.Admin
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserWithDistributorAttached(@PathVariable Long id){
        UserEntity userByDistributor = this.userService.findUserByDistributor(id);

        UserResponseDto userResponseDto = this.userMapper.toDto(userByDistributor);

        return ResponseEntity.ok(userResponseDto);
    }
}

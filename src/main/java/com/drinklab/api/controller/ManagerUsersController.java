package com.drinklab.api.controller;

import com.drinklab.api.dto.user.UserRequestUpdateDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.api.mapper.UserMapper;
import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.model.UserEntity;
import com.drinklab.domain.service.ManagerUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/manager")
public class ManagerUsersController {

    @Autowired
    private ManagerUsersService managerUsersService;

    @Autowired
    private UserMapper userMapper;

    @CheckAuthority.Master
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
            @RequestBody UserRequestUpdateDto userRequestUpdateDto) {

        UserEntity user = this.managerUsersService.findById(id);

        UserEntity userEntity = this.userMapper.copyUserProperties(userRequestUpdateDto, user);

        UserEntity userUpdated = this.managerUsersService.update(userEntity);

        UserResponseDto userResponseDto = this.userMapper.toDto(userUpdated);

        return ResponseEntity.ok(userResponseDto);

    }

}

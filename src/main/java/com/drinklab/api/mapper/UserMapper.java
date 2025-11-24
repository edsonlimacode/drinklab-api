package com.drinklab.api.mapper;

import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.domain.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "groupId", target = "group.id")
    UserEntity toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(UserEntity user);

    List<UserResponseDto> toListDto(List<UserEntity> users);
}

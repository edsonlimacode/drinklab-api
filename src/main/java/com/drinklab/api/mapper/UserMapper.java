package com.drinklab.api.mapper;

import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserRequestUpdateDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.domain.model.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "groupId", target = "group.id")
    UserEntity toEntity(UserRequestDto userRequestDto);

    UserEntity toEntity(UserRequestUpdateDto userRequestUpdateDto);

    UserResponseDto toDto(UserEntity user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copyUserProperties(UserRequestUpdateDto source, @MappingTarget UserEntity user);

    List<UserResponseDto> toListDto(List<UserEntity> users);
}

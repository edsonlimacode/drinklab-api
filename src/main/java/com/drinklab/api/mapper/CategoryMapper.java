package com.drinklab.api.mapper;


import com.drinklab.api.dto.category.CategoryRequestDto;
import com.drinklab.api.dto.category.CategoryResponseDto;
import com.drinklab.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto toDto(Category category);

    List<CategoryResponseDto> toListDto(List<Category> category);

}

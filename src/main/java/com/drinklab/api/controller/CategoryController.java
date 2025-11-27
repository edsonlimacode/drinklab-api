package com.drinklab.api.controller;

import com.drinklab.api.dto.category.CategoryRequestDto;
import com.drinklab.api.dto.category.CategoryResponseDto;
import com.drinklab.api.mapper.CategoryMapper;
import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.model.Category;
import com.drinklab.domain.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper mapper;

    @CheckAuthority.MasterOrAdmin
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDto>> listAll(Pageable pageable) {
        Page<Category> categories = this.categoryService.listAll(pageable);

        var categoryListDto = this.mapper.toListDto(categories.getContent());

        var categoryResponseDtos = new PageImpl<>(categoryListDto, pageable, categories.getTotalElements());
        return ResponseEntity.ok(categoryResponseDtos);
    }

    @CheckAuthority.Master
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = this.mapper.toEntity(categoryRequestDto);
        this.categoryService.create(category);
    }

    @CheckAuthority.Master
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = this.mapper.toEntity(categoryRequestDto);
        Category categoryUpdated = this.categoryService.update(id, category);

        CategoryResponseDto categoryResponseDto = this.mapper.toDto(categoryUpdated);

        return ResponseEntity.ok(categoryResponseDto);
    }

    @CheckAuthority.MasterOrAdmin
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id) {
        Category category = this.categoryService.findById(id);

        CategoryResponseDto categoryResponseDto = this.mapper.toDto(category);

        return ResponseEntity.ok(categoryResponseDto);
    }

    @CheckAuthority.Master
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.categoryService.delete(id);
    }

}

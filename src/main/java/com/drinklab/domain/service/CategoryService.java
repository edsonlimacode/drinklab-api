package com.drinklab.domain.service;


import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.domain.model.Category;
import com.drinklab.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> listAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    public void create(Category category) {
        this.categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {

        category.setId(id);

        this.findById(id);

        return this.categoryRepository.save(category);
    }

    public Category findById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Recurso de id: %d, não foi encontrado", id)));
    }

    public void delete(Long id) {
        Category category = this.findById(id);
        this.categoryRepository.delete(category);
    }
}

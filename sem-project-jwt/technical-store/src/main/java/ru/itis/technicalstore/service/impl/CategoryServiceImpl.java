package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.entity.Category;
import ru.itis.technicalstore.exception.exceptions.CategoryNotFoundException;
import ru.itis.technicalstore.repository.CategoryRepository;
import ru.itis.technicalstore.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }
}

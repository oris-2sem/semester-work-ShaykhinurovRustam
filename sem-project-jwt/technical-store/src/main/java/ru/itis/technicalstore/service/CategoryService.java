package ru.itis.technicalstore.service;

import ru.itis.technicalstore.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getById(long id);
}

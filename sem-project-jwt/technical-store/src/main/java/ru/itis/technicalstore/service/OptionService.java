package ru.itis.technicalstore.service;

import ru.itis.technicalstore.entity.Category;
import ru.itis.technicalstore.entity.Option;

import java.util.List;

public interface OptionService {
    List<Option> getOptionsByCategory(Category category);
}

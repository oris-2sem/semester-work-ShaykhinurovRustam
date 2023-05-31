package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.entity.Category;
import ru.itis.technicalstore.entity.Option;
import ru.itis.technicalstore.repository.OptionRepository;
import ru.itis.technicalstore.service.OptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    public List<Option> getOptionsByCategory(Category category){
        return optionRepository.findAllByCategoryOrderById(category);
    }
}

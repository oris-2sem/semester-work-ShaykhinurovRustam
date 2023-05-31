package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.category.CategoryRequestDto;
import ru.itis.technicalstore.dto.response.CategoryResponseDto;
import ru.itis.technicalstore.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDTO(Category category);
    Category fromDTO(CategoryRequestDto categoryRequestDto);
}

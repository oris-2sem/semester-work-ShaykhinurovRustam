package ru.itis.technicalstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.technicalstore.entity.Category;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;

    public static CategoryResponseDto fromCategory(Category category) {
        if (category == null) return null;
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

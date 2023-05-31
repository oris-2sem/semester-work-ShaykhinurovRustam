package ru.itis.technicalstore.dto.response;

import lombok.*;
import ru.itis.technicalstore.entity.*;
import ru.itis.technicalstore.entity.Value;
import ru.itis.technicalstore.mapper.CategoryMapper;
import ru.itis.technicalstore.mapper.ReviewMapper;
import ru.itis.technicalstore.mapper.ValueMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.itis.technicalstore.dto.response.CategoryResponseDto.fromCategory;
import static ru.itis.technicalstore.dto.response.ReviewResponseDto.fromReview;
import static ru.itis.technicalstore.dto.response.ValueResponseDto.fromValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String filename;
    private String name;
    private Integer price;
    private CategoryResponseDto category;
    private List<ValueResponseDto> values;
    private List<ReviewResponseDto> reviews;

    public static ProductResponseDto toDTO(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .filename(product.getFilename())
                .name(product.getName())
                .price(product.getPrice())
                .category(fromCategory(product.getCategory()))
                .values(fromValue(product.getValues()))
                .reviews(fromReview(product.getReviews()))
                .build();
    }

    public static List<ProductResponseDto> toDTO(List<Product> products) {
        return products
                .stream()
                .map(ProductResponseDto::toDTO)
                .collect(Collectors.toList());
    }
}

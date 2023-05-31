package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.product.ProductRequestDto;
import ru.itis.technicalstore.dto.response.ProductResponseDto;
import ru.itis.technicalstore.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toDTO(Product product);
    List<ProductResponseDto> toDTO(List<Product> product);

    Product fromDTO(ProductRequestDto productRequestDto);
}

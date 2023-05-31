package ru.itis.technicalstore.service;

import ru.itis.technicalstore.dto.request.product.ProductRequestDto;
import ru.itis.technicalstore.dto.response.ProductResponseDto;
import ru.itis.technicalstore.entity.Option;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.entity.User;

import java.util.List;

public interface ProductService {
    ProductResponseDto addProduct(long categoryId, ProductRequestDto product, List<String> values);
    ProductResponseDto updateProduct(long productId, String name, Integer price, List<String> values);
    void deleteProduct(long id);
    String findValueByProductAndOption(Product product, Option option);
    double getAvgRating(long productId);
    List<Product> getProducts(Long categoryId, String keyword, String categoryName);
    Product getSingleProduct(long id);
}

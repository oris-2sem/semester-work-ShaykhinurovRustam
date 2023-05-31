package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.dto.request.product.ProductRequestDto;
import ru.itis.technicalstore.dto.response.ProductResponseDto;
import ru.itis.technicalstore.entity.*;
import ru.itis.technicalstore.exception.exceptions.CategoryNotFoundException;
import ru.itis.technicalstore.exception.exceptions.ProductNotFoundException;
import ru.itis.technicalstore.mapper.ProductMapper;
import ru.itis.technicalstore.repository.*;
import ru.itis.technicalstore.service.ProductService;
import ru.itis.technicalstore.service.UserService;

import java.util.List;

import static ru.itis.technicalstore.dto.request.product.ProductRequestDto.fromDTO;
import static ru.itis.technicalstore.dto.response.ProductResponseDto.toDTO;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ValueRepository valueRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductMapper productMapper;

    public ProductResponseDto addProduct(long categoryId, ProductRequestDto productDto, List<String> values) {
        Product product = productMapper.fromDTO(productDto);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        List<Option> options = optionRepository.findAllByCategoryOrderById(category);
        product.setCategory(category);
        product.setUser(userService.getUser());
        productRepository.save(product);

        for (int i = 0; i < options.size(); i++) {
            Value value = new Value();
            value.setProduct(product);
            value.setOption(options.get(i));
            value.setValue(values.get(i));
            valueRepository.save(value);
        }
        return toDTO(product);
    }

    public ProductResponseDto updateProduct(long productId, String name, Integer price, List<String> values) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);

        productRepository.save(product);

        List<Option> options = optionRepository.findAllByCategoryOrderById(product.getCategory());
        if (values != null) {
            for (int i = 0; i < options.size(); i++) {
                Value value = valueRepository.findByProductAndOption(product, options.get(i));
                if (value == null) {
                    value = new Value();
                    value.setProduct(product);
                    value.setOption(options.get(i));
                    value.setValue(values.get(i));
                } else if (!value.getValue().equals(values.get(i))) {
                    value.setValue(values.get(i));
                }
                valueRepository.save(value);
            }
        }

        return toDTO(product);
    }

    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        System.out.println(product);
        valueRepository.deleteAll(product.getValues());
        reviewRepository.deleteAll(product.getReviews());
        productRepository.delete(product);
    }

    public String findValueByProductAndOption(Product product, Option option) {
        Value value = valueRepository.findByProductAndOption(product, option);
        return value != null ? value.getValue() : "";
    }

    public double getAvgRating(long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        List<Review> reviews = reviewRepository.findAllPublishedReviews(product);
        double avg = 0;
        if (!reviews.isEmpty()){
            for (Review review : reviews){
                avg = avg + review.getRating();
            }
            avg = avg / reviews.size();
        }
        return avg;
    }

    public List<Product> getProducts(Long categoryId, String keyword, String categoryName) {
        Sort sort = Sort.by(
                Sort.Order.asc("category"),
                Sort.Order.asc("id")
        );
        List<Product> products = productRepository.findAll(sort);

        if (keyword != null) {
            products = productRepository.search(keyword);
        }
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
            products = category.getProducts();
        }
        if (categoryName != null) {
            products = productRepository.findAllByCategoryName(categoryName);
        }

        return products;
    }


    public Product getSingleProduct(long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}

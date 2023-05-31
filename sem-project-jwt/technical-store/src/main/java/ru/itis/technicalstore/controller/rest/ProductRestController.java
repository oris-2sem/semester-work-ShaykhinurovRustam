package ru.itis.technicalstore.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.technicalstore.controller.rest.api.ProductApi;
import ru.itis.technicalstore.dto.request.product.ProductRequestDto;
import ru.itis.technicalstore.dto.response.ProductResponseDto;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.mapper.ProductMapper;
import ru.itis.technicalstore.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static ru.itis.technicalstore.dto.response.ProductResponseDto.toDTO;

@RequiredArgsConstructor
@RestController
public class ProductRestController implements ProductApi {
    private final ProductService productService;
    private final ProductMapper mapper;

    @Value("${image.directory}")
    private String imageDirectory;

    @Override
    public ResponseEntity<ProductResponseDto> addProduct(long categoryId, List<String> optionValues, ProductRequestDto productRequestDto, MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(imageDirectory);
            try {
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(imageDirectory + "/" + resultFilename));
                productRequestDto.setFilename(resultFilename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ProductResponseDto productResponseDto = productService.addProduct(categoryId, productRequestDto, optionValues);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(Long categoryId, String keyword, String catrgoryName) {
        List<Product> products = productService.getProducts(categoryId, keyword, catrgoryName);
        List<ProductResponseDto> productDtos = toDTO(products);

        return ResponseEntity.ok(productDtos);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<ProductResponseDto> saveUpdatedProduct(long productId, String name, Integer price, List<String> updatedValues) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.updateProduct(productId, name, price, updatedValues));
    }
}

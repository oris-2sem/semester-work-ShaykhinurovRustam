package ru.itis.technicalstore.dto.request.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.technicalstore.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    @NotNull private String name;
    @NotNull private Integer price;
    @JsonIgnore private MultipartFile file;
    @JsonIgnore private String filename;

    public static Product fromDTO(ProductRequestDto productRequestDto) {
        return Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .filename(productRequestDto.getFilename())
                .build();
    }

}

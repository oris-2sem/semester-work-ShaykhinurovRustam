package ru.itis.technicalstore.controller.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.technicalstore.dto.request.product.ProductRequestDto;
import ru.itis.technicalstore.exception.ExceptionDto;
import ru.itis.technicalstore.dto.response.ProductResponseDto;

import java.util.List;

@Tags(
        value = {
                @Tag(name = "Working with products")
        }
)
public interface ProductApi {
        @Operation(summary = "Creating product")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Product successfully created.",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                        })
        })
        @ApiResponse(responseCode = "404", description = "error information",
                content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionDto.class))
                }
        )

        @PostMapping(value="/api/add_product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
        ResponseEntity<ProductResponseDto> addProduct(@RequestParam long categoryId, @RequestParam List<String> optionValues,
                                                      @RequestPart("product") @Parameter(schema=@Schema(type = "string", format = "binary")) ProductRequestDto productRequestDto,
                                                      @RequestPart("image") MultipartFile file);

        @Operation(summary = "Getting a list of products")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "List of products",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                        })
        })
        @ApiResponse(responseCode = "404", description = "Error information",
                content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionDto.class))
                }
        )
        @GetMapping("/api/get_products")
        ResponseEntity<List<ProductResponseDto>> getAllProducts(
                @RequestParam(required = false) Long categoryId,
                @RequestParam(required = false) String keyword,
                @RequestParam(required = false) String categoryName
        );

        @Operation(summary = "Delete product")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "202", description = "Delete product"),
                @ApiResponse(responseCode = "404", description = "Error information",
                        content = {
                                @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ExceptionDto.class))
                        }
                )
        })
        @DeleteMapping("/api/{id}")
        ResponseEntity<?> deleteProduct(@PathVariable("id") Long id);

        @Operation(summary = "Update product")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "202", description = "Update Product",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                        }),
                @ApiResponse(responseCode = "404", description = "error information",
                        content = {
                                @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ExceptionDto.class))
                        }
                )
        })
        @PutMapping("/api/edit")
        ResponseEntity<ProductResponseDto> saveUpdatedProduct(
                @RequestParam long productId,
                @RequestParam(required = false) String name,
                @RequestParam(required = false) Integer price,
                @RequestParam(required = false) List<String> updatedValues);
}

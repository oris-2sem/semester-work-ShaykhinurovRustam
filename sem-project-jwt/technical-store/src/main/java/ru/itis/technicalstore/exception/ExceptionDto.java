package ru.itis.technicalstore.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "message of error")
public class ExceptionDto {
    @Schema(description = "error message")
    private String message;
    @Schema(description = "HTTP-code error", example = "404")
    private int status;
}

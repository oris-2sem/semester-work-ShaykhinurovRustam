package ru.itis.technicalstore.dto.request.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {
    private Long productId;
    private Integer rating;
    private String reviewText;
}

package ru.itis.technicalstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.technicalstore.entity.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.itis.technicalstore.dto.response.UserResponseDto.fromUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long id;
    private UserResponseDto user;
    private boolean isPublished;
    private int rating;
    private String text;
    private LocalDateTime created_at;

    public static ReviewResponseDto fromReview(Review review) {
        if (review == null) return null;
        return ReviewResponseDto.builder()
                .id(review.getId())
                .user(fromUser(review.getUser()))
                .isPublished(review.isPublished())
                .rating(review.getRating())
                .text(review.getText())
                .created_at(review.getCreated_at())
                .build();
    }

    public static List<ReviewResponseDto> fromReview(List<Review> reviews) {
        if (reviews == null) return null;
        return reviews
                .stream()
                .map(ReviewResponseDto::fromReview)
                .collect(Collectors.toList());
    }
}

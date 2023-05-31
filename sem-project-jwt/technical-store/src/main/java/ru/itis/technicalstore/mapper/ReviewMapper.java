package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.review.ReviewRequestDto;
import ru.itis.technicalstore.dto.response.ReviewResponseDto;
import ru.itis.technicalstore.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewResponseDto toDTO(Review review);
    List<ReviewResponseDto> toDTO(List<Review> reviews);

    Review fromDTO(ReviewRequestDto reviewRequestDto);
}

package ru.itis.technicalstore.service;


import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.entity.Review;
import ru.itis.technicalstore.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewService {

    void createReview(long productId, int rating, String reviewText);
    void deleteReview(long id);
    void postReview(long id);
    boolean isReviewPresent(Product product, User user);
    int getPublishedReviewsSize(Product product);
    String getReviewDate(LocalDateTime date);
    String getMonthOnRus(LocalDateTime date);
    List<Review> getPublishedReviews(Product product);
    List<Review> getReviewsByUser();
    List<Review> getNotPublishedReviews();
    Product findProductByReviewId(Long id);
}

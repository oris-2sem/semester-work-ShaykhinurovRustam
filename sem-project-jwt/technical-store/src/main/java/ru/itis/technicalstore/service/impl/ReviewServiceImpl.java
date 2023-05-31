package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.entity.Review;
import ru.itis.technicalstore.entity.User;
import ru.itis.technicalstore.exception.exceptions.ProductNotFoundException;
import ru.itis.technicalstore.exception.exceptions.ReviewNotFoundException;
import ru.itis.technicalstore.repository.ProductRepository;
import ru.itis.technicalstore.repository.ReviewRepository;
import ru.itis.technicalstore.service.ReviewService;
import ru.itis.technicalstore.service.UserService;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public void createReview(long productId, int rating, String reviewText) {
        Review review = new Review();
        review.setUser(userService.getUser());
        review.setProduct(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId)));
        review.setPublished(false);
        review.setRating(rating);
        review.setText(reviewText);
        review.setCreated_at(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public void deleteReview(long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.delete(review);
    }

    public void postReview(long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        review.setPublished(true);
        reviewRepository.save(review);
    }

    public boolean isReviewPresent(Product product, User user) {
        Review review = reviewRepository.findByUserAndProduct(user, product);
        return review != null;
    }

    public int getPublishedReviewsSize(Product product) {
        return reviewRepository.findAllPublishedReviews(product).size();
    }

    public String getReviewDate(LocalDateTime date) {
        return date.getDayOfMonth() + " "
                + getMonthOnRus(date) + " "
                + date.getYear();
    }

    public String getMonthOnRus(LocalDateTime date) {
        Month month = date.getMonth();
        Locale locale = new Locale("ru", "RU");
        return month.getDisplayName(TextStyle.FULL, locale);
    }

    public List<Review> getPublishedReviews(Product product) {
        return reviewRepository.findAllPublishedReviews(product);
    }

    public List<Review> getReviewsByUser() {
        return reviewRepository.findAllByUserOrderByIdDesc(userService.getUser());
    }

    public List<Review> getNotPublishedReviews() {
        return reviewRepository.findAllNotPublishedReviews();
    }

    @Override
    public Product findProductByReviewId(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        return review.getProduct();
    }
}

package ru.itis.technicalstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/for_checking")
    public String publishReview(
            @RequestParam(name = "product_id") long productId,
            @RequestParam(name = "rating") int rating,
            @RequestParam(name = "review") String reviewText,
            RedirectAttributes ra
    ) {
        reviewService.createReview(productId, rating, reviewText);
        ra.addFlashAttribute("reviewMessage", "Отзыв успешно отправлен на проверку");
        return "redirect:/products/" + productId;
    }

    @GetMapping("/review/delete/{id}")
    public String deleteReview(@PathVariable("id") long id) {
        Product product = reviewService.findProductByReviewId(id);
        reviewService.deleteReview(id);
        return "redirect:/products/" + product.getId();
    }
}


package ru.itis.technicalstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.technicalstore.service.OrderService;
import ru.itis.technicalstore.service.ReviewService;
import ru.itis.technicalstore.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    @GetMapping("/user")
    public String userInfoPage(Model model) {
        model.addAttribute("user", userService.getUser());
        return "user_information";
    }

    @GetMapping("/user/reviews")
    public String userReviewListPage(Model model) {
        model.addAttribute("reviews", reviewService.getReviewsByUser());
        return "user_reviews";
    }

    @GetMapping("/user/review/delete/{id}")
    public String userDeleteReview(
            @PathVariable("id") long id) {
        reviewService.deleteReview(id);
        return "redirect:/user/reviews";
    }

    @GetMapping("/user/orders")
    public String userOrderListPage(Model model) {
        model.addAttribute("orders", orderService.getOrdersByUser());
        return "user_orders";
    }

    @GetMapping("/forbidden")
    public String userAccessDenied(Model model) {
        model.addAttribute("message", "Доступ запрещен");
        model.addAttribute("statusCode", "403");
        return "error";
    }
}

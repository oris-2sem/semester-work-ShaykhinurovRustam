package ru.itis.technicalstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.technicalstore.entity.enums.Status;
import ru.itis.technicalstore.service.OrderService;
import ru.itis.technicalstore.service.ReviewService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ReviewService reviewService;
    private final OrderService orderService;

    @GetMapping("/reviews")
    public String reviewModifyPage(Model model) {
        model.addAttribute("reviews", reviewService.getNotPublishedReviews());
        return "admin_reviews";
    }

    @GetMapping(path = "/reviews/post")
    public String postReview(
            @RequestParam(name = "reviewId") long id
    ) {
        reviewService.postReview(id);
        return "redirect:/admin/reviews";
    }

    @GetMapping("/orders")
    public String getOrdersAsAdminOrModer(Model model) {
        model.addAttribute("orders", orderService.getOrdersBySeller());
        return "admin_orders";
    }

    @GetMapping("/orders/change_status")
    public String changeStatus(
            @RequestParam long orderId,
            @RequestParam Status status
    ) {
        orderService.changeStatus(orderId, status);
        return "redirect:/admin/orders";
    }
}

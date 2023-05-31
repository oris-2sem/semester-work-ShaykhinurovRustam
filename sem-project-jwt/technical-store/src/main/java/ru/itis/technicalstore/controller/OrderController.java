package ru.itis.technicalstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.technicalstore.service.CartService;
import ru.itis.technicalstore.service.OrderService;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartItemService;

    @GetMapping("/order")
    public String orderPage(Model model){
        model.addAttribute("cartItems", cartItemService.getItemsByUser());
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(
            @RequestParam String address
    ){
        orderService.createOrder(address);
        return "redirect:/user/orders";
    }
}

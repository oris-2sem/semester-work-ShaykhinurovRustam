package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.entity.Cart;
import ru.itis.technicalstore.entity.Order;
import ru.itis.technicalstore.entity.OrderProduct;
import ru.itis.technicalstore.entity.enums.Status;
import ru.itis.technicalstore.exception.exceptions.OrderNotFoundException;
import ru.itis.technicalstore.repository.CartRepository;
import ru.itis.technicalstore.repository.OrderProductRepository;
import ru.itis.technicalstore.repository.OrderRepository;
import ru.itis.technicalstore.service.OrderService;
import ru.itis.technicalstore.service.UserService;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;

    public void createOrder(String address) {
        List<Cart> cartItems = cartItemRepository.findAllByUserOrderById(userService.getUser());
        Order order = new Order();
        order.setUser(userService.getUser());
        order.setAddress(address);
        order.setStatus(Status.INSTOCK);
        order.setCreated_at(LocalDateTime.now());
        orderRepository.save(order);

        for (Cart cartItem : cartItems) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setAmount(cartItem.getAmount());
            orderProductRepository.save(orderProduct);
        }
        cartItemRepository.deleteAllByUser(userService.getUser());
    }

    public void changeStatus(long id, Status status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(status);
        orderRepository.save(order);
    }

    public List<Order> getOrdersByUser() {
        return orderRepository.findAllByUserOrderByIdDesc(userService.getUser());
    }

    public List<Order> getOrders() {
        return orderRepository.findAllByOrderById();
    }

    public List<Order> getOrdersBySeller() {
        return orderRepository.findAllBySeller(userService.getUser());
    }

    public List<Status> getStatuses() {
        return List.of(Status.values());
    }

    public String getOrderDate(LocalDateTime date) {
        return date.getDayOfMonth() + " "
                + getMonthOnRus(date) + " "
                + date.getYear() + " г. в "
                + date.getHour() + ":"
                + String.format("%02d", date.getMinute());
    }

    public String getMonthOnRus(LocalDateTime date) {
        Month month = date.getMonth();
        Locale locale = new Locale("ru", "RU");
        return month.getDisplayName(TextStyle.SHORT, locale);
    }
}

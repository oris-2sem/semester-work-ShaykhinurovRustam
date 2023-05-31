package ru.itis.technicalstore.service;

import org.springframework.stereotype.Service;
import ru.itis.technicalstore.entity.Order;
import ru.itis.technicalstore.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    void createOrder(String address);
    void changeStatus(long id, Status status);
    List<Order> getOrdersByUser();
    List<Order> getOrders();
    List<Order> getOrdersBySeller();
    List<Status> getStatuses();
    String getOrderDate(LocalDateTime date);
    String getMonthOnRus(LocalDateTime date);
}

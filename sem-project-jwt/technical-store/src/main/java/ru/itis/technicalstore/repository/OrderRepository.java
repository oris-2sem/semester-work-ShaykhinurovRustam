package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.technicalstore.entity.Order;
import ru.itis.technicalstore.entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderById();
    List<Order> findAllByUserOrderByIdDesc(User user);

    @Query("SELECT o.order FROM OrderProduct o WHERE o.product.user = :seller")
    List<Order> findAllBySeller(User seller);
}

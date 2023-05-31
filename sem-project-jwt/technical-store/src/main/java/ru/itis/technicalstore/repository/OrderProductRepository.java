package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.technicalstore.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}

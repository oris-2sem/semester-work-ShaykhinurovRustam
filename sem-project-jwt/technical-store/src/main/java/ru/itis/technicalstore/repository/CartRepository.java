package ru.itis.technicalstore.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.technicalstore.entity.Cart;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.entity.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByOrderById();

    List<Cart> findAllByUserOrderById(User user);
    Cart findByUserAndProduct(User user, Product product);

    @Transactional
    void deleteAllByUser(User user);
}

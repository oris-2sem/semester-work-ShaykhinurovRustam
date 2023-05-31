package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.technicalstore.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    List<Product> search(String keyword);

    @Query("SELECT p FROM Product p WHERE p.category IN (SELECT c FROM Category c WHERE c.name = :name)")
    List<Product> findAllByCategoryName(String name);
}

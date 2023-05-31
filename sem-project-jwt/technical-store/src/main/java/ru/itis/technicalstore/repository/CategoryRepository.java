package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.technicalstore.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

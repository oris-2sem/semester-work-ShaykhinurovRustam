package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.technicalstore.entity.Category;
import ru.itis.technicalstore.entity.Option;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByCategoryOrderById(Category category);
}

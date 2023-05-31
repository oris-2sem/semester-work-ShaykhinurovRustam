package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.technicalstore.entity.Option;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.entity.Value;

public interface ValueRepository extends JpaRepository<Value, Long> {
    Value findByProductAndOption(Product product, Option option);

}

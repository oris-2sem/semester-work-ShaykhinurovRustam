package ru.itis.technicalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.technicalstore.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}

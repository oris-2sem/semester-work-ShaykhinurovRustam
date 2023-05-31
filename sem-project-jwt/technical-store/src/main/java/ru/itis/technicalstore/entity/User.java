package ru.itis.technicalstore.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.itis.technicalstore.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.ORDINAL)
    private Role role;

    private String login;
    private String password;
    private String name;
    private String lastname;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<Review> reviews;

    @OneToMany(mappedBy = "user")
    List<Product> products;
}
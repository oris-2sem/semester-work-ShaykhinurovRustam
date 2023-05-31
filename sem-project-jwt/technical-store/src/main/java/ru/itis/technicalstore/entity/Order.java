package ru.itis.technicalstore.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.itis.technicalstore.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Status status;

    private String address;

    private LocalDateTime created_at;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;
}

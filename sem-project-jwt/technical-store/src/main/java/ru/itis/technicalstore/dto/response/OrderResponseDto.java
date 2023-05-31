package ru.itis.technicalstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.technicalstore.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Status status;
    private String address;
    private LocalDateTime created_at;
    private List<Long> orderProductsIds;
}

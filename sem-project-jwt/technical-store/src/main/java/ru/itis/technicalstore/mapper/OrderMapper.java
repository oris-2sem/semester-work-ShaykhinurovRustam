package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.order.OrderRequestDto;
import ru.itis.technicalstore.dto.response.OrderResponseDto;
import ru.itis.technicalstore.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toDTO(Order order);
    Order fromDTO(OrderRequestDto orderRequestDto);
}

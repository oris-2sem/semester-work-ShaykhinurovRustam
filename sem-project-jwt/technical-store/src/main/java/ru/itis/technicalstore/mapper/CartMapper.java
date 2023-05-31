package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.cart.CartRequestDto;
import ru.itis.technicalstore.dto.response.CartResponseDto;
import ru.itis.technicalstore.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponseDto toDTO(Cart cart);
    Cart fromDTO(CartRequestDto cartRequestDto);
}

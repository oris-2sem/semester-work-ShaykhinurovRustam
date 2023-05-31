package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.user.UserRequestDto;
import ru.itis.technicalstore.dto.response.UserResponseDto;
import ru.itis.technicalstore.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDTO(User user);
    User fromDTO(UserRequestDto userRequestDto);
}

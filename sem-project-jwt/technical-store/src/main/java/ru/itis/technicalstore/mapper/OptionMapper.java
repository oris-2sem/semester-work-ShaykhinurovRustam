package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.option.OptionRequestDto;
import ru.itis.technicalstore.dto.response.OptionResponseDto;
import ru.itis.technicalstore.entity.Option;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    OptionResponseDto toDTO(Option option);
    Option fromDTO(OptionRequestDto optionRequestDto);
}

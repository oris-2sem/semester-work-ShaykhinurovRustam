package ru.itis.technicalstore.mapper;

import org.mapstruct.Mapper;
import ru.itis.technicalstore.dto.request.value.ValueRequestDto;
import ru.itis.technicalstore.dto.response.ValueResponseDto;
import ru.itis.technicalstore.entity.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValueMapper {
    ValueResponseDto toDTO(Value value);
    List<ValueResponseDto> toDTO(List<Value> values);

    Value fromDTO(ValueRequestDto valueRequestDto);
}

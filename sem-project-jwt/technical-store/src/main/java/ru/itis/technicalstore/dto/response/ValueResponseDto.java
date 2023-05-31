package ru.itis.technicalstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.technicalstore.entity.Value;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValueResponseDto {
    private Long id;
    private String value;

    public static ValueResponseDto fromValue(Value value) {
        if (value == null) return null;
        return ValueResponseDto.builder()
                .id(value.getId())
                .value(value.getValue())
                .build();
    }

    public static List<ValueResponseDto> fromValue(List<Value> values) {
        if (values == null) return null;
        return values
                .stream()
                .map(ValueResponseDto::fromValue)
                .collect(Collectors.toList());
    }
}

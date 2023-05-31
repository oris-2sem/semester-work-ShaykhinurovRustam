package ru.itis.technicalstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionResponseDto {
    private Long id;
    private String name;
    private Long categoryId;
    private List<ValueResponseDto> valuesIds;
}

package ru.itis.technicalstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.technicalstore.entity.User;
import ru.itis.technicalstore.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private Role role;
    private String login;
    private String name;
    private String lastname;
    private LocalDateTime createdAt;

    public static UserResponseDto fromUser(User user) {
        if (user == null) return null;
        return UserResponseDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .login(user.getLogin())
                .name(user.getName())
                .lastname(user.getLastname())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

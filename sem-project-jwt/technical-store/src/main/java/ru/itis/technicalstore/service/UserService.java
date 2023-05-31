package ru.itis.technicalstore.service;

import ru.itis.technicalstore.dto.request.user.UserRequestDto;
import ru.itis.technicalstore.dto.response.UserResponseDto;
import ru.itis.technicalstore.entity.User;

public interface UserService {
    UserResponseDto saveNewUser(UserRequestDto user);
    User getUser();
    boolean isAuthenticated();
    boolean isAdminOrSeller();
    boolean isAdmin();
    String greeting();
    String getMessageBasedOnHour();
    User findByLogin(String login);
}

package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.dto.request.user.UserRequestDto;
import ru.itis.technicalstore.dto.response.UserResponseDto;
import ru.itis.technicalstore.entity.User;
import ru.itis.technicalstore.entity.enums.Role;
import ru.itis.technicalstore.mapper.UserMapper;
import ru.itis.technicalstore.repository.UserRepository;
import ru.itis.technicalstore.service.UserService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserResponseDto saveNewUser(UserRequestDto userDto) {
        User user = mapper.fromDTO(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        userRepository.save(user);

        return mapper.toDTO(user);
    }

    public User findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElse(null);
    }

    public User getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<User> user = userRepository.findByLogin(authentication.getName());
        return user.orElse(null);
    }

    public boolean isAuthenticated() {
        return getUser() != null;
    }

    public boolean isAdminOrSeller() {
        if (isAuthenticated()) {
            return getUser().getRole() == Role.SELLER || getUser().getRole() == Role.ADMIN;
        }
        return false;
    }

    public boolean isAdmin() {
        if (isAuthenticated()) {
            return getUser().getRole() == Role.ADMIN;
        }
        return false;
    }

    public String greeting() {
        return isAuthenticated() ? getMessageBasedOnHour() + ", " + getUser().getName() : getMessageBasedOnHour();
    }

    public String getMessageBasedOnHour() {
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);
        if (time >= 6 && time <= 11) {
            return "Доброе утро";
        } else if (time >= 12 && time <= 16) {
            return "Добрый день";
        } else if (time >= 17 && time <= 21) {
            return "Добрый вечер";
        } else {
            return "Доброй ночи";
        }
    }
}

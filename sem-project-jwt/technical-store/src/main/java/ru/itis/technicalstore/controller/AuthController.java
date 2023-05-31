package ru.itis.technicalstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.technicalstore.dto.request.user.UserRequestDto;
import ru.itis.technicalstore.entity.User;
import ru.itis.technicalstore.service.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/registration")
    public String userSignUp(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "user_registration";
    }

    @GetMapping("/login")
    public String userSignIn() {
        return "user_login";
    }

    @PostMapping("/registration")
    public String saveNewUser(@Valid UserRequestDto newUserDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/registration?error";
        }

        User user = userService.findByLogin(newUserDto.getLogin());
        if (user != null && user.getLogin() != null && !user.getLogin().isEmpty()) {
            return "redirect:/registration?exists";
        }

        userService.saveNewUser(newUserDto);
        return "redirect:/login";
    }
}

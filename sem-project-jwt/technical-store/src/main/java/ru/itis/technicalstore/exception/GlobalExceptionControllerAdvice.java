package ru.itis.technicalstore.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionControllerAdvice.class);

    @ExceptionHandler({Exception.class})
    public String handleError(Exception exception, Model model) {
        String message = (exception != null ? exception.getMessage() : "Неизвестная ошибка");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn("Пользователь: " + auth.getName()
                    + " получил ошибку: "
                    + message);
        }

        model.addAttribute("message", message);
        model.addAttribute("statusCode", "404");
        return "error";
    }

}

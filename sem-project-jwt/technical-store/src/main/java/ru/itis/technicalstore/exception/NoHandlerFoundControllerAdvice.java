package ru.itis.technicalstore.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NoHandlerFoundControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionControllerAdvice.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn("Пользователь: " + auth.getName()
                    + " попытался зайти на несуществующую страницу: ");
        }

        model.addAttribute("message", "Запрашиваемый ресурс не найден");
        model.addAttribute("statusCode", "404");
        return "error";
    }
}

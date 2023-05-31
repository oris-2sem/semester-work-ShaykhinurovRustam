package ru.itis.technicalstore.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AccessDeniedAdvice implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedAdvice.class);

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn("Пользователь: " + auth.getName()
                    + " попытался зайти на защищенную страницу: "
                    + request.getRequestURI());
        }

        response.sendRedirect(request.getContextPath() + "/forbidden");
    }
}

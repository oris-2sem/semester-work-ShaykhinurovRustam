package ru.itis.technicalstore.security.util.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.technicalstore.security.util.AuthorizationsCookieUtil;


@Component
@RequiredArgsConstructor
public class AuthorizationCookieUtilImpl implements AuthorizationsCookieUtil {
    @Override
    public boolean hasAuthorizationToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    jwtCookie = cookie;
                    break;
                }
            }
        }
        return jwtCookie != null;
    }

    @Override
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    jwtCookie = cookie;
                    break;
                }
            }
        }
        return jwtCookie.getValue();
    }
}

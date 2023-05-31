package ru.itis.technicalstore.security.util;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthorizationsCookieUtil {
    boolean hasAuthorizationToken(HttpServletRequest request);
    String getToken(HttpServletRequest request);
}

package ru.itis.technicalstore.security.util;

import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Map;

public interface JWTUtil {
    String generateAccessToken(String subject, String authority);
    Map<String, String> parseToken(String token) throws JWTVerificationException;
}

package ru.itis.technicalstore.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.itis.technicalstore.security.details.UserDetailsImpl;
import ru.itis.technicalstore.security.authentication.RefreshTokenAuthentication;
import ru.itis.technicalstore.security.util.AuthorizationsCookieUtil;
import ru.itis.technicalstore.security.util.JWTUtil;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthorizationsCookieUtil authorizationsCookieUtil;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(AuthenticationConfiguration authenticationConfiguration,
                                AuthorizationsCookieUtil authorizationsCookieUtil,
                                JWTUtil jwtUtil, ObjectMapper objectMapper) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.authorizationsCookieUtil = authorizationsCookieUtil;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (authorizationsCookieUtil.hasAuthorizationToken(request)) {
            String refreshToken = authorizationsCookieUtil.getToken(request);
            RefreshTokenAuthentication authentication = new RefreshTokenAuthentication(refreshToken);
            return super.getAuthenticationManager().authenticate(authentication);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        GrantedAuthority grantedAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow();
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String token = jwtUtil.generateAccessToken(userDetails.getUsername(), grantedAuthority.toString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, Collections.singleton(grantedAuthority));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        response.sendRedirect("/products");
        objectMapper.writeValue(response.getWriter(), token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendRedirect("/login?error");
    }
}

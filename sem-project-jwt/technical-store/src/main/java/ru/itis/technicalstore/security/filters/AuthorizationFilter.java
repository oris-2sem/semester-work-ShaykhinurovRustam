package ru.itis.technicalstore.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.technicalstore.security.util.AuthorizationsCookieUtil;
import ru.itis.technicalstore.security.util.JWTUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    public static final String AUTHENTICATION_PATH = "/login/token";
    private final AuthorizationsCookieUtil authorizationsCookieUtil;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTHENTICATION_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationsCookieUtil.hasAuthorizationToken(request)) {
            String token = authorizationsCookieUtil.getToken(request);

            try {
                Map<String, String> data = jwtUtil.parseToken(token);
                Authentication authentication = new UsernamePasswordAuthenticationToken(data.get("sub"),
                        null, Collections.singletonList(new SimpleGrantedAuthority(data.get("role"))));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

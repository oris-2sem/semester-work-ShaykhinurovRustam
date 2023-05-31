package ru.itis.technicalstore.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.technicalstore.exception.AccessDeniedAdvice;
import ru.itis.technicalstore.security.filters.AuthenticationFilter;
import ru.itis.technicalstore.security.filters.AuthorizationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsServiceImpl;
    String authenticationPath = AuthorizationFilter.AUTHENTICATION_PATH;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationFilter authorizationFilter,
                                                           AuthenticationFilter authenticationFilterFilter) throws Exception {
        http.csrf().ignoringRequestMatchers("/api/**");

        authenticationFilterFilter.setFilterProcessesUrl(authenticationPath);
        http.addFilter(authenticationFilterFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().xssProtection();

        http.authorizeHttpRequests((request) -> request
            .requestMatchers(
                    "/user/**",
                    "/cart/**",
                    "/order/**",
                    "/swagger-ui/**").authenticated()
            .requestMatchers(
                    "/admin/reviews/**", "/api/**").hasAuthority("admin")
            .requestMatchers(
                "/products/add/**",
                "/products/edit/**",
                "/products/delete/**",
                "/orders/**").hasAnyAuthority("admin", "seller")
            .anyRequest().permitAll()
        );

        http.formLogin((form) -> {
            form.loginPage("/login")
                    .failureForwardUrl("/login?error")
                    .defaultSuccessUrl("/products", true);
        });

        http.logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "Authorization");

        http.exceptionHandling()
                .accessDeniedHandler(new AccessDeniedAdvice());

        return http.build();
    }

    @Autowired
    public void build(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder);
    }
}

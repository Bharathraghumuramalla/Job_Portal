package com.spring.jobPortal.SpringSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserService userService;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;



    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // 2. Allow H2 to render in browser (frames)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )

                // 3. 🔥 REGISTER AUTH PROVIDER (YOU WERE MISSING THIS)
                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        // DEV
                        .requestMatchers("/h2-console/**").permitAll()

                        // ADMIN AREA
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // EMPLOYEE AREA
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")

                        // STUDENT AREA
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // SHARED READ APIs
                        .requestMatchers(
                                "/students/**",
                                "/jobs/**"
                        ).hasAnyRole("ADMIN", "EMPLOYEE", "STUDENT")

                        // EVERYTHING ELSE
                        .anyRequest().authenticated()
                )

                // 5. HTTP Basic for Postman
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}

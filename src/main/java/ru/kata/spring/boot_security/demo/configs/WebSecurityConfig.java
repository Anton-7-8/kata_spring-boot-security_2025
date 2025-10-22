package ru.kata.spring.boot_security.demo.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImp;

/**
 * Класс конфигурации безопасности Spring Security.
 * Настраивает правила доступа, аутентификацию и шифрование паролей.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    // Логгер для отслеживания конфигурации безопасности.
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsServiceImp userDetailsServiceImp;

    /**
     * Конструктор для инъекции зависимостей.
     *
     * @param successUserHandler    Обработчик успешной аутентификации.
     * @param userDetailsServiceImp Сервис для загрузки данных пользователя.
     */
    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsServiceImp userDetailsServiceImp) {
        logger.info("Инициализация WebSecurityConfig");
        this.successUserHandler = successUserHandler;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }
    /**
     * Бин для цепочки фильтров безопасности.
     * Настраивает правила авторизации, отключение CSRF, форму логина и логаут.
     *
     * @param http Объект HttpSecurity для настройки.
     * @return SecurityFilterChain Цепочка фильтров.
     * @throws Exception Если возникает ошибка конфигурации.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Настройка SecurityFilterChain");
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/current").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/user").hasRole("USER")
                        .requestMatchers("/login", "/js/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf ->csrf.disable())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .failureUrl("/login?error")
                        .successHandler(successUserHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .userDetailsService(userDetailsServiceImp);

        return http.build();
    }

    /**
     * Бин для шифровщика паролей.
     * Использует BCrypt для безопасного хэширования.
     * @return PasswordEncoder Шифровщик паролей.
     */
    @Bean

    public PasswordEncoder getPasswordEncoder() {
        logger.info("Создание PasswordEncoder");
        return new BCryptPasswordEncoder();
    }
}
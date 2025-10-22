package ru.kata.spring.boot_security.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для загрузки данных пользователя для Spring Security.
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImp implements UserDetailsService {
    // Логгер для отслеживания операций в сервисе.
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImp.class);
    private final

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

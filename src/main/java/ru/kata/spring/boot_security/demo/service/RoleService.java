package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для работы с ролями.
 */
@Component
public interface RoleService {

    /**
     * Возвращает список всех ролей.
     * @return Список ролей.
     */
    List<Role> findAll();

    /**
     * Находит роль по ID.
     * @param id Идентификатор роли.
     * @return Optional с ролью.
     */
    Optional<Role> findById (Long id);

    /**
     * Находит роль по ID с обработкой исключения.
     * @param id Идентификатор роли.
     * @return Роль.
     */
    Role getById(long id);
    /**
     * Сохраняет роль в базе данных.
     * @param role Роль для сохранения.
     */
    void save(Role role);

    /**
     * Находит роль по имени.
     * @param roleName Имя роли.
     * @return Роль.
     */
    Role findByRoleName(String roleName);
}

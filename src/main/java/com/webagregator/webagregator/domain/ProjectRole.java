package com.webagregator.webagregator.domain;

import lombok.Getter;

/**
 * Enum, представляющий роли в проекте.
 */
@Getter
public enum ProjectRole {
    /**
     * Роль тимлида.
     */
    TEAM_LEAD("TeamLead"),
    /**
     * Роль бэкендера.
     */
    BACKEND("Backend"),
    /**
     * Роль дизайнера.
     */
    DESIGNER("Designer"),/**
     * Роль аналитика.
     */
    SCIENTIST("Scientist");
    //другие роли добавлю по необходимости или когда получу список всевозможных ролей в проекте

    /**
     * Строковое представление роли.
     */
    private final String role;
    /**
     * Конструктор с параметром, устанавливающий роль.
     *
     * @param role Строковое представление роли.
     */
    ProjectRole(String role) {
        this.role = role;
    }
}

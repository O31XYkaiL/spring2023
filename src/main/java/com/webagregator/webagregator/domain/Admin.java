package com.webagregator.webagregator.domain;

import lombok.*;
import java.util.List;

@Data
public class Admin {
    /**
     * ID админа
     */
    private Long id;
    /**
     * Логин админа
     */
    private String username;
    /**
     * Пароль админа
     */
    private String password;
    /**
     * Список проектов, поступающих на проверку админу
     */
    private List<Project> projectsForReview;
}
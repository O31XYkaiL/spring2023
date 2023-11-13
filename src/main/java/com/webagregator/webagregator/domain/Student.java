package com.webagregator.webagregator.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class Student {
    /**
     * ID студента
     */
    private Long id;
    /**
     * Урфушная(!) почта
     */
    private String email;
    /**
     * Пароль
     */
    private String password;
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String lastName;
    /**
     * Академическая группа
     */
    private String academicGroup;
    /**
     * Количество голосов у студента
     */
    private int voteCount;
    /**
     * Роль студента в команде
     */
    private ProjectRole roleInProject;
    /**
     * Проекты, за которые проголосовал студент
     */
    List<Project> votedProjects;

    /**
     * Инициализация списка
     */
    public Student() {
        this.votedProjects = new ArrayList<>();
    }
}
package com.webagregator.webagregator.domain;

import lombok.*;

@Data
public class Student {
    private Long id; //id студента
    private String email; //урфушная(!) почта
    private String password; //пароль
    private String firstName; //имя
    private String lastName; //фамилия
    private String academicGroup; //академ группа
}

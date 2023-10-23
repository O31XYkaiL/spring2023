package com.webagregator.webagregator.domain;

import lombok.*;

@Data
public class Admin {
    private Long id; //id админа
    private String username; //логин админа
    private String password; //пароль админа

    // позже добавлю поля для связей с другими сущностями студент/тима

    // доп геттеры и сеттеры связей ?????????
}
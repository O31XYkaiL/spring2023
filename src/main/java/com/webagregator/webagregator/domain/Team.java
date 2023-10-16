package com.webagregator.webagregator.domain;

import lombok.*;

@Data
public class Team {
    private Long id; //id тимы
    private String teamName; //название тимы
    private String teamDescription; //описание тимы
    private Long teamLeaderId; //id студента-тимлида

    // позже добавлю поля для связей с другими сущностями студент/проект

    // доп геттеры и сеттеры связей ?????????

}
package com.webagregator.webagregator.domain;

import lombok.*;

@Data
public class Project {
    private Long id; //id проекта
    private String projectName; //название проекта
    private String projectDescription; //описание проекта
    private String howToPlay; //описание как играть
    private String coverImage; //обложка проекта
    private String gameplayVideo; //видео геймплея проекта
    private String projectCategory; //категория проекта
    private String projectTheme; //тема проекта
    private String repositoryLink; //ссылка на гит
    private byte[] projectArchive; //архив с проектом, который должен проигрываться в браузере

    // позже добавлю поля для связей с другими сущностями студент/админ/тима

    // доп геттеры и сеттеры связей ?????????
}


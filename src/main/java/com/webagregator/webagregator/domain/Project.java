package com.webagregator.webagregator.domain;

import jakarta.persistence.Lob;
import lombok.*;

@Data
public class Project {
    /**
     * ID проекта
     */
    private Long id;
    /**
     * Название проекта
     */
    private String projectName;
    /**
     * Описание проекта
     */
    private String projectDescription;
    /**
     * Описание "как играть"
     */
    private String howToPlay;
    /**
     * Обложка проекта
     */
    private String coverImage;
    /**
     * Видео геймплея проекта
     */
    private String gameplayVideo;
    /**
     * Категория проекта
     */
    private String projectCategory;
    /**
     * Подкатегория проекта
     */
    private String projectSubcategory;
    /**
     * Тема проекта
     */
    private String projectTheme;
    /**
     * Ссылка на репозиторий проекта
     */
    private String repositoryLink;
    /**
     * Архив с проектом, который должен проигрываться в браузере
     */
    @Lob
    private byte[] projectArchive;
    /**
     * Студент-создатель проекта (связь с сущностью Student)
     */
    private Student creator;
    /**
     * Администратор, проводящий модерацию
     */
    private Admin moderator;
    /**
     * Количество голосов, полученных за время голосования
     */
    private int voteCount;
}

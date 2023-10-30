package com.webagregator.webagregator.domain;

import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
public class Team {
    /**
     * ID тимы
     */
    private Long id;
    /**
     * Название тимы
     */
    private String teamName;
    /**
     * Описание тимы
     */
    private String teamDescription;
    /**
     * Студент-тимлид
     */
    private Student teamLeader;
    /**
     * Список студентов, являющихся членами данной команды.
     */
    @Getter
    @OneToMany(mappedBy = "team")
    private List<Student> teamMembers;
}
package com.webagregator.webagregator.app.repositories;

import com.webagregator.webagregator.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // Здесь вы можете добавить кастомные методы для работы с сущностью Team, если это необходимо
}

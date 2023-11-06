package com.webagregator.webagregator.app.repositories;

import com.webagregator.webagregator.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCategoryAndSubcategory(String category, String subcategory);
}

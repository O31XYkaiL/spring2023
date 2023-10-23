package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.TeamRepository;
import com.webagregator.webagregator.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team getTeamById(Long id) {
        log.info("Getting team by ID: {}", id);
        return teamRepository.findById(id).orElse(null);
    }

    public void createTeam(Team team) {
        log.info("Creating a new team: {}", team);
        teamRepository.save(team);
    }

    public void updateTeam(Team team) {
        log.info("Updating team: {}", team);
        teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        log.info("Deleting team by ID: {}", id);
        teamRepository.deleteById(id);
    }
}

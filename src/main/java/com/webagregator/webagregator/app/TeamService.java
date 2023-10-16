package com.webagregator.webagregator.app;

import com.webagregator.webagregator.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TeamService {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    public TeamService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // метод для получения инфы о команде по ее id
    public Team getTeamById(Long id) {
        logger.info("Getting team by ID: " + id);
        String sql = "SELECT * FROM teams WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Team team = new Team();
            team.setId(rs.getLong("id"));
            team.setTeamName(rs.getString("team_name"));
            team.setTeamDescription(rs.getString("team_description"));
            team.setTeamLeaderId(rs.getLong("team_leader_id"));
            return team;
        });
    }

    // метод для создания новой тимы в бд
    public void createTeam(Team team) {
        logger.info("Creating a new team: " + team);
        String sql = "INSERT INTO teams (team_name, team_description, team_leader_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, team.getTeamName(), team.getTeamDescription(), team.getTeamLeaderId());
    }

    // метод для обновления инфы об уже существующей тиме
    public void updateTeam(Team team) {
        logger.info("Updating team: " + team);
        String sql = "UPDATE teams SET team_name = ?, team_description = ?, team_leader_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, team.getTeamName(), team.getTeamDescription(), team.getTeamLeaderId(), team.getId());
    }

    // метод для удаления существующей тимы из бд
    public void deleteTeam(Long id) {
        logger.info("Deleting team by ID: " + id);
        String sql = "DELETE FROM teams WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.TeamService;
import com.webagregator.webagregator.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TeamServiceTests {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamService(jdbcTemplate);
    }

    @Test
    public void testGetTeamById() {
        Long teamId = 1L;
        Team expectedTeam = new Team();
        expectedTeam.setId(teamId);
        expectedTeam.setTeamName("TestTeam");

        when(jdbcTemplate.queryForObject(anyString(), eq(Team.class), any(Object[].class))).thenReturn(expectedTeam);

        Team actualTeam = teamService.getTeamById(teamId);

        assertEquals(expectedTeam, actualTeam);
    }

    @Test
    public void testCreateTeam() {
        Team teamToCreate = new Team();
        teamToCreate.setTeamName("NewTeam");

        teamService.createTeam(teamToCreate);

        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testUpdateTeam() {
        Team teamToUpdate = new Team();
        teamToUpdate.setId(1L);
        teamToUpdate.setTeamName("UpdatedTeam");

        teamService.updateTeam(teamToUpdate);

        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testDeleteTeam() {
        Long teamId = 1L;

        teamService.deleteTeam(teamId);

        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }
}

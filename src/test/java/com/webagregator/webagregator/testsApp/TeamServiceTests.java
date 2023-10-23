package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.repositories.TeamRepository;
import com.webagregator.webagregator.domain.Team;
import com.webagregator.webagregator.app.services.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

public class TeamServiceTests {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        teamService = new TeamService(teamRepository);
    }

    @Test
    public void testGetTeamById() {
        Team mockTeam = new Team();
        mockTeam.setId(1L);
        mockTeam.setTeamName("Mock Team");

        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));

        Team result = teamService.getTeamById(1L);

        assertEquals(mockTeam, result);
    }

    @Test
    public void testCreateTeam() {
        Team newTeam = new Team();
        newTeam.setId(2L);
        newTeam.setTeamName("New Team");

        teamService.createTeam(newTeam);

        Mockito.verify(teamRepository).save(newTeam);
    }

    @Test
    public void testUpdateTeam() {
        Team existingTeam = new Team();
        existingTeam.setId(3L);
        existingTeam.setTeamName("Existing Team");

        Mockito.when(teamRepository.save(existingTeam)).thenReturn(existingTeam);

        teamService.updateTeam(existingTeam);

        Mockito.verify(teamRepository).save(existingTeam);
    }

    @Test
    public void testDeleteTeam() {
        teamService.deleteTeam(4L);

        Mockito.verify(teamRepository).deleteById(4L);
    }
}

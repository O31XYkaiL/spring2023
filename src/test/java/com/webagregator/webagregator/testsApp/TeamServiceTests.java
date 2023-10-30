package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.app.repositories.TeamRepository;
import com.webagregator.webagregator.app.services.TeamService;
import com.webagregator.webagregator.domain.Student;
import com.webagregator.webagregator.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TeamServiceTests {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTeamById() {
        Long teamId = 1L;
        Team team = new Team();
        team.setId(teamId);

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));

        Team result = teamService.getTeamById(teamId);

        assertNotNull(result);
        assertEquals(teamId, result.getId());
    }

    @Test
    public void testGetTeamById_WhenTeamNotFound() {
        Long teamId = 1L;

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.empty());

        Team result = teamService.getTeamById(teamId);

        assertNull(result);
    }

    @Test
    public void testCreateTeam() {
        Team team = new Team();

        teamService.createTeam(team);

        Mockito.verify(teamRepository).save(team);
    }

    @Test
    public void testUpdateTeam() {
        Team team = new Team();

        teamService.updateTeam(team);

        Mockito.verify(teamRepository).save(team);
    }

    @Test
    public void testDeleteTeam() {
        Long teamId = 1L;

        teamService.deleteTeam(teamId);

        Mockito.verify(teamRepository).deleteById(teamId);
    }

    @Test
    public void testGetTeamLeaderByTeamId() {
        Long teamId = 1L;
        Team team = new Team();
        Student teamLeader = new Student();
        teamLeader.setRoleInProject("Team Leader");
        team.setTeamLeader(teamLeader);

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));

        Student result = teamService.getTeamLeaderByTeamId(teamId);

        assertNotNull(result);
        assertEquals("Team Leader", result.getRoleInProject());
    }

    @Test
    public void testGetTeamLeaderByTeamId_WhenTeamNotFound() {
        Long teamId = 1L;

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.empty());

        Student result = teamService.getTeamLeaderByTeamId(teamId);

        assertNull(result);
    }

    @Test
    public void testAddStudentToTeamWithRole() {
        Long teamId = 1L;
        Team team = new Team();
        Student teamLeader = new Student();
        teamLeader.setRoleInProject("Team Leader");
        team.setTeamLeader(teamLeader);

        Student studentToAdd = new Student();
        studentToAdd.setId(2L);

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));
        when(studentRepository.findStudentByLastNameAndFirstName("Doe", "John")).thenReturn(studentToAdd);

        Team updatedTeam = teamService.addStudentToTeamWithRole(teamId, "Doe", "John", "Member");

        assertNotNull(updatedTeam);
        assertTrue(updatedTeam.getTeamMembers().contains(studentToAdd));
        assertEquals("Member", studentToAdd.getRoleInProject());
    }

    @Test
    public void testAddStudentToTeamWithRole_WhenTeamNotFound() {
        Long teamId = 1L;

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.empty());

        Team updatedTeam = teamService.addStudentToTeamWithRole(teamId, "Doe", "John", "Member");

        assertNull(updatedTeam);
    }

    @Test
    public void testAddStudentToTeamWithRole_WhenTeamLeaderCannotAdd() {
        Long teamId = 1L;
        Team team = new Team();
        Student teamLeader = new Student();
        teamLeader.setRoleInProject("Member");
        team.setTeamLeader(teamLeader);

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));

        Team updatedTeam = teamService.addStudentToTeamWithRole(teamId, "Doe", "John", "Member");

        assertNull(updatedTeam);
    }

    @Test
    public void testAddStudentToTeamWithRole_WhenStudentNotFound() {
        Long teamId = 1L;
        Team team = new Team();
        Student teamLeader = new Student();
        teamLeader.setRoleInProject("Team Leader");
        team.setTeamLeader(teamLeader);

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));
        when(studentRepository.findStudentByLastNameAndFirstName("Doe", "John")).thenReturn(null);

        Team updatedTeam = teamService.addStudentToTeamWithRole(teamId, "Doe", "John", "Member");

        assertNull(updatedTeam);
    }

    @Test
    public void testAddStudentToTeamWithRole_WhenStudentIsTeamLeader() {
        Long teamId = 1L;
        Team team = new Team();
        Student teamLeader = new Student();
        teamLeader.setRoleInProject("Team Leader");
        team.setTeamLeader(teamLeader);

        Student studentToAdd = new Student();
        studentToAdd.setRoleInProject("Team Leader");

        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));
        when(studentRepository.findStudentByLastNameAndFirstName("Doe", "John")).thenReturn(studentToAdd);

        Team updatedTeam = teamService.addStudentToTeamWithRole(teamId, "Doe", "John", "Member");

        assertNull(updatedTeam);
    }
}
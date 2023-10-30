package com.webagregator.webagregator.testsDomain;

import com.webagregator.webagregator.domain.Student;
import com.webagregator.webagregator.domain.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Test
    public void testTeamId() {
        Team team = new Team();
        team.setId(1L);

        assertEquals(1L, team.getId());
    }

    @Test
    public void testTeamName() {
        Team team = new Team();
        team.setTeamName("Team A");

        assertEquals("Team A", team.getTeamName());
    }

    @Test
    public void testTeamDescription() {
        Team team = new Team();
        team.setTeamDescription("This is a test team");

        assertEquals("This is a test team", team.getTeamDescription());
    }

    @Test
    public void testTeamLeader() {
        Team team = new Team();
        Student teamLeader = new Student();
        teamLeader.setId(1L);
        team.setTeamLeader(teamLeader);

        assertEquals(teamLeader, team.getTeamLeader());
    }

    @Test
    public void testTeamMembers() {
        Team team = new Team();
        List<Student> members = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(2L);
        Student student2 = new Student();
        student2.setId(3L);
        members.add(student1);
        members.add(student2);

        team.setTeamMembers(members);

        assertEquals(members, team.getTeamMembers());
    }
}

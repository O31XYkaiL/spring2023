package com.webagregator.webagregator.testsDomain;

import com.webagregator.webagregator.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamTest {
    private Team team;
    @BeforeEach
    void setUp() {
        team = new Team();
    }

    @Test
    void testTeamSetterGetter() {
        team.setTeamName("TeamA");
        team.setTeamDescription("Description");
        team.setTeamLeaderId(1L);

        assertEquals("TeamA", team.getTeamName());
        assertEquals("Description", team.getTeamDescription());
        assertEquals(1L, team.getTeamLeaderId());
    }
}

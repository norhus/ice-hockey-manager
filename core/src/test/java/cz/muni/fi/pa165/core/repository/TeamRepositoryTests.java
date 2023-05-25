package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class TeamRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    public void setUp() {
        League league1 = new League();
        Team team1 = new Team();

        league1.setName("League 1");
        team1.setName("Team 1");
        team1.setLeague(league1);

        entityManager.persist(league1);
        entityManager.persist(team1);
    }

    @Test
    void testFindByName() {
        String teamName = "Team 1";

        Team foundTeam = teamRepository.findByName(teamName);

        Assertions.assertNotNull(foundTeam);
        Assertions.assertEquals(teamName, foundTeam.getName());
    }

    @Test
    void testFindByLeagueName() {
        String leagueName = "League 1";

        List<Team> teams = teamRepository.findByLeagueName(leagueName);

        Assertions.assertEquals(1, teams.size());
        Team foundTeam = teams.get(0);
        Assertions.assertEquals(leagueName, foundTeam.getLeague().getName());
    }
}

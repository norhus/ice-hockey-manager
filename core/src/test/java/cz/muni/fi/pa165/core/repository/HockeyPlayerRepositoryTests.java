package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.List;

@DataJpaTest
class HockeyPlayerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HockeyPlayerRepository hockeyPlayerRepository;

    @BeforeEach
    public void setUp() {
        hockeyPlayerRepository.deleteAll();

        League league = new League();
        league.setName("League 1");
        entityManager.persist(league);

        Team team1 = new Team();
        team1.setName("Team 1");
        team1.setLeague(league);
        entityManager.persist(team1);

        Team team2 = new Team();
        team2.setName("Team 2");
        team2.setLeague(league);
        entityManager.persist(team2);

        HockeyPlayer player1 = new HockeyPlayer();
        player1.setFirstName("John");
        player1.setLastName("Doe");
        player1.setTeam(team1);
        player1.setDateOfBirth(Instant.parse("1974-10-22T13:13:13.715Z"));
        player1.setPosition("winger");
        player1.setSkating(99);
        player1.setPhysical(99);
        player1.setShooting(99);
        player1.setDefense(99);
        player1.setPuckSkills(99);
        player1.setSenses(99);
        entityManager.persist(player1);

        HockeyPlayer player2 = new HockeyPlayer();
        player2.setFirstName("Alex");
        player2.setLastName("Johnson");
        player2.setDateOfBirth(Instant.parse("1974-10-22T13:13:13.715Z"));
        player2.setPosition("winger");
        player2.setSkating(99);
        player2.setPhysical(99);
        player2.setShooting(99);
        player2.setDefense(99);
        player2.setPuckSkills(99);
        player2.setSenses(99);
        entityManager.persist(player2);
    }

    @Test
    void testFindAllByTeamIdIsNull() {
        List<HockeyPlayer> players = hockeyPlayerRepository.findAllByTeamIdIsNull();

        Assertions.assertEquals(1, players.size());
        Assertions.assertEquals("Alex", players.get(0).getFirstName());
        Assertions.assertEquals("Johnson", players.get(0).getLastName());
    }
}

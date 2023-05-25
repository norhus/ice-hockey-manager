package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.entity.Match;
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
class MatchRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    public void setUp() {
        matchRepository.deleteAll();

        League league = new League();
        league.setName("League 1");
        entityManager.persist(league);

        Team homeTeam = new Team();
        homeTeam.setName("Home Team");
        homeTeam.setLeague(league);

        Team awayTeam = new Team();
        awayTeam.setName("Away Team");
        awayTeam.setLeague(league);

        entityManager.persist(homeTeam);
        entityManager.persist(awayTeam);

        Match match1 = new Match();
        match1.setDateOfMatch(Instant.parse("2023-04-10T19:10:25.00Z"));
        match1.setHomeTeam(homeTeam);
        match1.setAwayTeam(awayTeam);

        Match match2 = new Match();
        match2.setDateOfMatch(Instant.parse("2023-04-11T19:10:25.00Z"));
        match2.setHomeTeam(homeTeam);
        match2.setAwayTeam(awayTeam);

        match2.setHomeGoals(2);
        match2.setAwayGoals(1);
        entityManager.persist(match1);
        entityManager.persist(match2);
    }

    @Test
    void testFindByHomeTeamLeagueName() {
        String leagueName = "League 1";

        List<Match> matches = matchRepository.findByHomeTeamLeagueName(leagueName);

        Assertions.assertEquals(2, matches.size());
        for (Match match : matches) {
            Assertions.assertEquals(leagueName, match.getHomeTeam().getLeague().getName());
        }
    }

    @Test
    void testFindUnplayedMatchesBeforeNow() {
        List<Match> unplayedMatches = matchRepository.findUnplayedMatchesBeforeNow();

        Assertions.assertEquals(1, unplayedMatches.size());
        Match match = unplayedMatches.get(0);
        Assertions.assertNull(match.getHomeGoals());
    }

    @Test
    void testFindPlayedMatchesByLeague() {
        String leagueName = "League 1";

        List<Match> playedMatches = matchRepository.findPlayedMatchesByLeague(leagueName);

        Assertions.assertEquals(1, playedMatches.size());
        Match match = playedMatches.get(0);
        Assertions.assertEquals(leagueName, match.getHomeTeam().getLeague().getName());
        Assertions.assertNotNull(match.getHomeGoals());
    }
}

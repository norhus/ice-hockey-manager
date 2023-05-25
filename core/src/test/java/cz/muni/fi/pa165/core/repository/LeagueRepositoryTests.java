package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.League;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class LeagueRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LeagueRepository leagueRepository;

    @BeforeEach
    public void setUp() {
        League league1 = new League();
        League league2 = new League();

        league1.setName("League 1");
        league2.setName("League 2");

        entityManager.persist(league1);
        entityManager.persist(league2);
    }

    @Test
    void testFindByName() {
        String leagueName = "League 1";

        League foundLeague = leagueRepository.findByName(leagueName);

        Assertions.assertNotNull(foundLeague);
        Assertions.assertEquals(leagueName, foundLeague.getName());
    }
}

package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByHomeTeamLeagueName(String leagueName);

    @Query("SELECT m FROM Match m WHERE m.dateOfMatch < NOW() AND m.homeGoals IS NULL")
    List<Match> findUnplayedMatchesBeforeNow();
}
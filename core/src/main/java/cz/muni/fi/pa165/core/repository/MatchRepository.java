package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByHomeTeamLeagueName(String leagueName);

    @Query("SELECT m FROM Match m WHERE m.dateOfMatch <= :today AND m.homeTeam.league.name = :leagueName  AND m.homeGoals IS NOT NULL")
    List<Match> findPlayedMatchesByLeague(Instant today, String leagueName);

}
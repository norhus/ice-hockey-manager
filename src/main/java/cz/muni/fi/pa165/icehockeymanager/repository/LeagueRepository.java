package cz.muni.fi.pa165.icehockeymanager.repository;

import cz.muni.fi.pa165.icehockeymanager.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {

    League findByName(String name);
}

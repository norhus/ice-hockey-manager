package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    League findByName(String name);
}

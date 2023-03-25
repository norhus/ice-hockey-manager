package cz.muni.fi.pa165.core.repository;


import cz.muni.fi.pa165.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);

    List<Team> findByLeagueName(String leagueName);
}

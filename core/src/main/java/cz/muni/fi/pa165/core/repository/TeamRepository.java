package cz.muni.fi.pa165.core.repository;


import cz.muni.fi.pa165.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);

    List<Team> findByLeagueName(String leagueName);
}

package cz.muni.fi.pa165.icehockeymanager.repository;


import cz.muni.fi.pa165.icehockeymanager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);
}

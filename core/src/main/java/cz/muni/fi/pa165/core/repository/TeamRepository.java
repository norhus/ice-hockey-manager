package cz.muni.fi.pa165.core.repository;


import cz.muni.fi.pa165.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);
}

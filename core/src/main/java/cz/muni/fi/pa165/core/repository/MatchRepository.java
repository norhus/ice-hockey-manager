package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>
{

}
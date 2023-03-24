package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HockeyPlayerRepository extends JpaRepository<HockeyPlayer, Long> {

    List<HockeyPlayer>  findAllByTeamIdIsNull();
}

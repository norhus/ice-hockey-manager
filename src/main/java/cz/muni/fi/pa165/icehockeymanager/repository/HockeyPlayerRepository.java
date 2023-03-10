package cz.muni.fi.pa165.icehockeymanager.repository;

import cz.muni.fi.pa165.icehockeymanager.entity.HockeyPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HockeyPlayerRepository extends JpaRepository<HockeyPlayer, Long> {
}

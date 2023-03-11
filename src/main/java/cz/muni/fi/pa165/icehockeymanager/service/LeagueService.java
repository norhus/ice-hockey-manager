package cz.muni.fi.pa165.icehockeymanager.service;

import cz.muni.fi.pa165.icehockeymanager.dto.LeagueDto;
import cz.muni.fi.pa165.icehockeymanager.mapper.LeagueMapper;
import cz.muni.fi.pa165.icehockeymanager.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, LeagueMapper leagueMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueMapper = leagueMapper;
    }

    public LeagueDto findByName(String name) {
       return leagueMapper.toDto(leagueRepository.findByName(name));
    }
}

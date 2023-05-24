package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.core.mapper.LeagueMapper;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<LeagueDto> findAll() {
        return leagueRepository.findAll().stream()
                .map(leagueMapper::toDto)
                .toList();
    }

    public LeagueDto create(LeagueDto leagueDto) {
        return leagueMapper.toDto(leagueRepository.save(leagueMapper.toEntity(leagueDto)));
    }
}

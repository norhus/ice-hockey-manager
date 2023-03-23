package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.mapper.LeagueMapper;
import cz.muni.fi.pa165.core.mapper.MatchMapper;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import cz.muni.fi.pa165.core.repository.MatchRepository;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    public List<MatchDto> findAll() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .toList();
    }
}
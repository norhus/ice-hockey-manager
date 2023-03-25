package cz.muni.fi.pa165.core.service;


import cz.muni.fi.pa165.core.entity.Match;
import cz.muni.fi.pa165.core.mapper.MatchMapper;
import cz.muni.fi.pa165.core.repository.MatchRepository;
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

    public MatchDto create(MatchDto matchDto) {
        return matchMapper.toDto(matchRepository.save(matchMapper.toEntity(matchDto)));
    }

    public MatchDto update(MatchDto matchDto) {
        Match match = matchRepository.findById(matchDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Not found match with id: " + matchDto.id()));

        return matchMapper.toDto(matchRepository.save(matchMapper.updateMatchFromMatchDto(matchDto, match)));
    }
}
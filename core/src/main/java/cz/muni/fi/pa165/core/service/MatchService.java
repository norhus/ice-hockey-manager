package cz.muni.fi.pa165.core.service;


import cz.muni.fi.pa165.core.entity.Match;
import cz.muni.fi.pa165.core.mapper.MatchMapper;
import cz.muni.fi.pa165.core.repository.MatchRepository;
import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        return matchMapper.toDto(matchRepository.save(matchMapper.update(matchDto, match)));
    }

    public List<MatchDto> findByLeagueName(String leagueName) {
        return matchRepository.findByHomeTeamLeagueName(leagueName).stream()
                .map(matchMapper::toDto)
                .toList();
    }

    public List<MatchDto> findUnplayedMatchesBeforeToday(Instant today) {
        return matchRepository.findUnplayedMatchesBeforeToday(today).stream()
                .map(matchMapper::toDto)
                .toList();
    }

    public List<MatchDto> playUnplayedMatches() {
        List<MatchDto> unplayedMatches = findUnplayedMatchesBeforeToday(Instant.now());
        List<MatchDto> playedMatches = new ArrayList<>();
        Random randomNum = new Random();
        for (MatchDto match: unplayedMatches) {
            playedMatches.add(update(new MatchDto(match.id(), match.dateOfMatch(), randomNum.nextInt(6),
                    randomNum.nextInt(6), match.homeTeam(), match.awayTeam())));
        }
        return playedMatches;
    }
}

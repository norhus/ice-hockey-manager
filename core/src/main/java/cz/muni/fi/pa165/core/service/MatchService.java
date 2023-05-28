package cz.muni.fi.pa165.core.service;


import cz.muni.fi.pa165.core.entity.Match;
import cz.muni.fi.pa165.core.mapper.MatchMapper;
import cz.muni.fi.pa165.core.repository.MatchRepository;
import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for Match
 */
@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    /**
     * Retrieves all matches.
     *
     * @return A list of MatchDto objects representing all matches.
     */
    public List<MatchDto> findAll() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .toList();
    }

    /**
     * Creates a new match.
     *
     * @param matchDto The MatchDto object representing the match to be created.
     * @return The MatchDto object representing the created match.
     */
    public MatchDto create(MatchDto matchDto) {
        return matchMapper.toDto(matchRepository.save(matchMapper.toEntity(matchDto)));
    }

    /**
     * Updates an existing match.
     *
     * @param matchDto The MatchDto object representing the updated match.
     * @return The MatchDto object representing the updated match.
     * @throws IllegalArgumentException if the match with the given ID is not found.
     */
    public MatchDto update(MatchDto matchDto) {
        Match match = matchRepository.findById(matchDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Not found match with id: " + matchDto.id()));

        return matchMapper.toDto(matchRepository.save(matchMapper.update(matchDto, match)));
    }

    /**
     * Retrieves matches by the name of the league.
     *
     * @param leagueName The name of the league.
     * @return A list of MatchDto objects representing the matches in the specified league.
     */
    public List<MatchDto> findByLeagueName(String leagueName) {
        return matchRepository.findByHomeTeamLeagueName(leagueName).stream()
                .map(matchMapper::toDto)
                .toList();
    }

    /**
     * Retrieves unplayed matches before the current time.
     *
     * @return A list of MatchDto objects representing the unplayed matches.
     */
    public List<MatchDto> findUnplayedMatchesBeforeNow() {
        return matchRepository.findUnplayedMatchesBeforeNow().stream()
                .map(matchMapper::toDto)
                .toList();
    }

    /**
     * Plays the unplayed matches and updates their scores randomly.
     *
     * @return A list of MatchDto objects representing the played matches.
     */
    public List<MatchDto> playUnplayedMatches() {
        List<MatchDto> unplayedMatches = findUnplayedMatchesBeforeNow();
        List<MatchDto> playedMatches = new ArrayList<>();
        Random randomNum = new Random();
        for (MatchDto match: unplayedMatches) {
            playedMatches.add(update(new MatchDto(match.id(), match.dateOfMatch(), randomNum.nextInt(6),
                    randomNum.nextInt(6), match.homeTeam(), match.awayTeam())));
        }
        return playedMatches;
    }

    /**
     * Retrieves played matches in a specific league.
     *
     * @param leagueName The name of the league.
     * @return A list of MatchDto objects representing the played matches in the specified league.
     */
    public List<MatchDto> findPlayedMatchesByLeague(String leagueName) {
        return matchRepository.findPlayedMatchesByLeague(leagueName).stream()
                .map(matchMapper::toDto)
                .toList();
    }

}

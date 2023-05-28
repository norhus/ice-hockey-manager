package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.core.mapper.LeagueMapper;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for League
 */
@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;

    /**
     * Creates a LeagueService instance
     *
     * @param leagueRepository LeagueRepository instance
     * @param leagueMapper LeagueMapper instance
     */
    @Autowired
    public LeagueService(LeagueRepository leagueRepository, LeagueMapper leagueMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueMapper = leagueMapper;
    }

    /**
     * Get League by name
     *
     * @param name league name
     * @return LeagueDto instance by name
     */
    public LeagueDto findByName(String name) {
       return leagueMapper.toDto(leagueRepository.findByName(name));
    }

    /**
     * Get all leagues
     *
     * @return all LeagueDto instances
     */
    public List<LeagueDto> findAll() {
        return leagueRepository.findAll().stream()
                .map(leagueMapper::toDto)
                .toList();
    }

    /**
     * Create league
     *
     * @param leagueDto LeagueDto instance
     * @return new LeagueDto instance
     */
    public LeagueDto create(LeagueDto leagueDto) {
        return leagueMapper.toDto(leagueRepository.save(leagueMapper.toEntity(leagueDto)));
    }
}

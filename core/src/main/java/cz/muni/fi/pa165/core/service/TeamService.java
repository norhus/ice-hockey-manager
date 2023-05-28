package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.entity.Team;
import cz.muni.fi.pa165.core.mapper.TeamMapper;
import cz.muni.fi.pa165.core.repository.HockeyPlayerRepository;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import cz.muni.fi.pa165.core.repository.TeamRepository;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Team
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final HockeyPlayerRepository hockeyPlayerRepository;
    private final LeagueRepository leagueRepository;

    private final TeamMapper teamMapper;

    /**
     * Creates a TeamService instance
     *
     * @param teamRepository TeamRepository instance
     * @param teamMapper TeamMapper instance
     * @param hockeyPlayerRepository HockeyPlayerRepository instance
     * @param leagueRepository LeagueRepository instance
     */
    @Autowired
    public TeamService(TeamRepository teamRepository,
                       TeamMapper teamMapper,
                       HockeyPlayerRepository hockeyPlayerRepository,
                       LeagueRepository leagueRepository
    ) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.hockeyPlayerRepository = hockeyPlayerRepository;
        this.leagueRepository = leagueRepository;
    }

    /**
     * Get Team by name
     * @param name Team name
     *
     * @return TeamDto instance
     */
    public TeamDto findByName(String name) {
        return teamMapper.toDto(teamRepository.findByName(name));
    }

    /**
     * Get all Teams
     *
     * @return TeamDto instances
     */
    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .toList();
    }

    /**
     * Get all Teams by League name
     * @param leagueName League name
     *
     * @return TeamDto instances by League name
     */
    public List<TeamDto> findByLeagueName(String leagueName) {
        return teamRepository.findByLeagueName(leagueName).stream()
                .map(teamMapper::toDto)
                .toList();
    }

    /**
     * Add Hockey Players to Team
     * @param id Team id
     * @param hockeyPlayerIds Hockey Player ids
     *
     * @return updated TeamDto instance
     */
    public TeamDto addHockeyPlayersByIds(long id, List<Long> hockeyPlayerIds) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + id));
        List<HockeyPlayer> hockeyPlayers = hockeyPlayerRepository.findAllById(hockeyPlayerIds);

        team.addHockeyPlayers(hockeyPlayers);

        return teamMapper.toDto(teamRepository.save(team));
    }

    /**
     * Remove Hockey Players from Team
     * @param id Team id
     * @param hockeyPlayerIds Hockey Player ids
     *
     * @return updated TeamDto instance
     */
    public TeamDto removeHockeyPlayersByIds(long id, List<Long> hockeyPlayerIds) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + id));
        List<HockeyPlayer> hockeyPlayers = hockeyPlayerRepository.findAllById(hockeyPlayerIds);

        team.removeHockeyPlayers(hockeyPlayers);

        return teamMapper.toDto(teamRepository.save(team));
    }

    /**
     * Change Team League
     * @param id Team id
     * @param leagueDto LeagueDto instance
     *
     * @return updated TeamDto instance
     */
    public TeamDto changeLeague(long id, LeagueDto leagueDto) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + id));
        League league = leagueRepository.findById(leagueDto.id()).orElseThrow(() -> new IllegalArgumentException("Not found league with id: " + id));

        league.addTeam(team);

        return teamMapper.toDto(teamRepository.save(team));
    }

    /**
     * Create Team
     * @param teamDto TeamDto instance
     *
     * @return new TeamDto instance
     */
    public TeamDto create(TeamDto teamDto) {
        return teamMapper.toDto(teamRepository.save(teamMapper.toEntity(teamDto)));
    }
}

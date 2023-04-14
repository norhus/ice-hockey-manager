package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.entity.Team;
import cz.muni.fi.pa165.core.mapper.HockeyPlayerMapper;
import cz.muni.fi.pa165.core.mapper.TeamMapper;
import cz.muni.fi.pa165.core.repository.HockeyPlayerRepository;
import cz.muni.fi.pa165.core.repository.TeamRepository;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service public class TeamService {

    private final TeamRepository teamRepository;
    private final HockeyPlayerRepository hockeyPlayerRepository;

    private final TeamMapper teamMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper, HockeyPlayerRepository hockeyPlayerRepository) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.hockeyPlayerRepository = hockeyPlayerRepository;
    }

    public TeamDto findByName(String name) {
        return teamMapper.toDto(teamRepository.findByName(name));
    }

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .toList();
    }

    public List<TeamDto> findByLeagueName(String leagueName) {
        return teamRepository.findByLeagueName(leagueName).stream()
                .map(teamMapper::toDto)
                .toList();
    }

    public TeamDto addHockeyPlayersByIds(long id, List<Long> hockeyPlayerIds) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + id));
        List<HockeyPlayer> hockeyPlayers = hockeyPlayerRepository.findAllById(hockeyPlayerIds);

        team.addHockeyPlayers(hockeyPlayers);

        return teamMapper.toDto(teamRepository.save(team));
    }

    public TeamDto removeHockeyPlayersByIds(long id, List<Long> hockeyPlayerIds) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + id));
        List<HockeyPlayer> hockeyPlayers = hockeyPlayerRepository.findAllById(hockeyPlayerIds);

        team.removeHockeyPlayers(hockeyPlayers);

        return teamMapper.toDto(teamRepository.save(team));
    }
}

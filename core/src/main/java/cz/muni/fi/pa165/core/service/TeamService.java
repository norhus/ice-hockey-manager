package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.model.dto.TeamDto;
import cz.muni.fi.pa165.core.mapper.TeamMapper;
import cz.muni.fi.pa165.core.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public TeamDto findByName(String name) {
        return teamMapper.toDto(teamRepository.findByName(name));
    }

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .toList();
    }
}

package cz.muni.fi.pa165.icehockeymanager.service;

import cz.muni.fi.pa165.icehockeymanager.dto.TeamDto;
import cz.muni.fi.pa165.icehockeymanager.mapper.TeamMapper;
import cz.muni.fi.pa165.icehockeymanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import cz.muni.fi.pa165.core.entity.Team;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toEntity(TeamDto teamDto);

    @Mapping(target = "league.teams", ignore = true)
    TeamDto toDto(Team team);
}

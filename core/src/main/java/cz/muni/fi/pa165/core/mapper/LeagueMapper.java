package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface LeagueMapper {

    @Mapping(target = "teams.league", ignore = true)
    LeagueDto toDto(League league);

    League toEntity(LeagueDto leagueDto);
}

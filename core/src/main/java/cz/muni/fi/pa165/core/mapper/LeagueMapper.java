package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueMapper {

    LeagueDto toDto(League league);

    League toEntity(LeagueDto leagueDto);
}

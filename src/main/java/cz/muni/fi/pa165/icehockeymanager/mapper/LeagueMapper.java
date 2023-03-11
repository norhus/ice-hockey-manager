package cz.muni.fi.pa165.icehockeymanager.mapper;

import cz.muni.fi.pa165.icehockeymanager.dto.LeagueDto;
import cz.muni.fi.pa165.icehockeymanager.entity.League;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueMapper {

    LeagueDto toDto(League league);
}

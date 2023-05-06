package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HockeyPlayerMapper {

    HockeyPlayer toEntity(HockeyPlayerDto hockeyPlayerDto);

    @Mapping(source = "team", target = "teamDto")
    @Mapping(target = "teamDto.league.teams", ignore = true)
    @Mapping(target = "teamDto.hockeyPlayers", ignore = true)
    HockeyPlayerDto toDto(HockeyPlayer hockeyPlayer);

    HockeyPlayer update(HockeyPlayerDto hockeyPlayerDto, @MappingTarget HockeyPlayer hockeyPlayer);
}

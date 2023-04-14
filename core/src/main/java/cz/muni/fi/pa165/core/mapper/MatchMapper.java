package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.core.entity.Match;
import cz.muni.fi.pa165.model.dto.MatchDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "id", ignore = true)
    Match toEntity(MatchDto matchDto);

    @Mapping(target = "homeTeam.league", ignore = true)
    @Mapping(target = "awayTeam.league", ignore = true)
    MatchDto toDto(Match match);

    Match update(MatchDto matchDto, @MappingTarget Match match);
}

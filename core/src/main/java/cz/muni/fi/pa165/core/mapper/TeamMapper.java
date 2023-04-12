package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.model.dto.TeamDto;
import cz.muni.fi.pa165.core.entity.Team;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team toEntity(TeamDto teamDto);

    TeamDto toDto(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDto teamDto, @MappingTarget Team team);
}

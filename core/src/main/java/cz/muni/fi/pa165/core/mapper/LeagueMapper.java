package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.core.entity.League;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LeagueMapper {

    LeagueDto toDto(League league);

    League toEntity(LeagueDto leagueDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(LeagueDto leagueDto, @MappingTarget League league);
}

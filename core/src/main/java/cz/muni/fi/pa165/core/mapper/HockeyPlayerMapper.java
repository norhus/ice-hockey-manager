package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HockeyPlayerMapper {

    @Mapping(target = "id", ignore = true)
    HockeyPlayer toEntity(HockeyPlayerDto hockeyPlayerDto);

    HockeyPlayerDto toDto(HockeyPlayer hockeyPlayer);

    HockeyPlayer update(HockeyPlayerDto hockeyPlayerDto, @MappingTarget HockeyPlayer hockeyPlayer);
}

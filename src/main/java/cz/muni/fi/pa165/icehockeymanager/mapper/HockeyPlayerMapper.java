package cz.muni.fi.pa165.icehockeymanager.mapper;

import cz.muni.fi.pa165.icehockeymanager.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.icehockeymanager.entity.HockeyPlayer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HockeyPlayerMapper {

    @Mapping(target = "id", ignore = true)
    HockeyPlayer toEntity(HockeyPlayerDto hockeyPlayerDto);

    HockeyPlayerDto toDto(HockeyPlayer hockeyPlayer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HockeyPlayer updateHockeyPlayerFromHockeyPlayerDto(HockeyPlayerDto hockeyPlayerDto, @MappingTarget HockeyPlayer hockeyPlayer);
}

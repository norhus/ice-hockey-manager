package cz.muni.fi.pa165.icehockeymanager.mapper;

import cz.muni.fi.pa165.icehockeymanager.dto.UserDto;
import cz.muni.fi.pa165.icehockeymanager.dto.UserRegisterDto;
import cz.muni.fi.pa165.icehockeymanager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterDto userRegisterDto);

    @Mapping(source = "password", target = "password", ignore = true)
    UserDto toDto(User user);
}

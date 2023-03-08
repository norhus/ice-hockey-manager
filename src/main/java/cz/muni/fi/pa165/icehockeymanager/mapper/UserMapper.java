package cz.muni.fi.pa165.icehockeymanager.mapper;

import cz.muni.fi.pa165.icehockeymanager.dto.UserDto;
import cz.muni.fi.pa165.icehockeymanager.dto.UserRegisterDto;
import cz.muni.fi.pa165.icehockeymanager.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDetails(User user);

    User toEntity(UserRegisterDto userRegisterDto);

    UserDto toDto(User user);
}

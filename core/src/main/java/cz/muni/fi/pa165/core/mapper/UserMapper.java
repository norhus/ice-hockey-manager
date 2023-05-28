package cz.muni.fi.pa165.core.mapper;

import cz.muni.fi.pa165.model.dto.UserDto;
import cz.muni.fi.pa165.model.dto.UserRegisterDto;
import cz.muni.fi.pa165.core.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterDto userRegisterDto);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}

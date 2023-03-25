package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.model.dto.UserDto;
import cz.muni.fi.pa165.model.dto.UserRegisterDto;
import cz.muni.fi.pa165.core.entity.User;
import cz.muni.fi.pa165.core.mapper.UserMapper;
import cz.muni.fi.pa165.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto create(UserRegisterDto userRegisterDto) {
        User user = userMapper.toEntity(userRegisterDto);

        return userMapper.toDto(userRepository.save(user));
    }
}

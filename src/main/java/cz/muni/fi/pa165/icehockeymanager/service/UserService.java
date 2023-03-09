package cz.muni.fi.pa165.icehockeymanager.service;

import cz.muni.fi.pa165.icehockeymanager.dto.UserDto;
import cz.muni.fi.pa165.icehockeymanager.dto.UserRegisterDto;
import cz.muni.fi.pa165.icehockeymanager.dto.UserWithJwtDto;
import cz.muni.fi.pa165.icehockeymanager.entity.User;
import cz.muni.fi.pa165.icehockeymanager.mapper.UserMapper;
import cz.muni.fi.pa165.icehockeymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public UserDto create(UserRegisterDto userRegisterDto) {
        User user = userMapper.toEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }

    public UserWithJwtDto login(UserDto userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password()));

        return new UserWithJwtDto(userDto.email(), userDto.role(), jwtTokenService.generateJwtToken(userDto));
    }
}

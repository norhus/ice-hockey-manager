package cz.muni.fi.pa165.icehockeymanager.service;

import cz.muni.fi.pa165.icehockeymanager.dto.UserDto;
import cz.muni.fi.pa165.icehockeymanager.dto.UserRegisterDto;
import cz.muni.fi.pa165.icehockeymanager.entity.User;
import cz.muni.fi.pa165.icehockeymanager.mapper.UserMapper;
import cz.muni.fi.pa165.icehockeymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email: %s not found.", email)));

        return userMapper.toUserDetails(user);
    }

    public UserDto create(UserRegisterDto userRegisterDto) {
        User user = userMapper.toEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }
}

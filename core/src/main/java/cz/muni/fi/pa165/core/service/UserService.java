package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.User;
import cz.muni.fi.pa165.core.repository.UserRepository;
import cz.muni.fi.pa165.model.shared.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<SimpleGrantedAuthority> getAuthoritiesByEmail(String email) {
        var user = createIfNotExist(email);

        return user.getAuthorities();
    }

    public User createIfNotExist(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            user = userRepository.save(new User(null, email, email, Role.ROLE_USER));
        }

        return user;
    }
}

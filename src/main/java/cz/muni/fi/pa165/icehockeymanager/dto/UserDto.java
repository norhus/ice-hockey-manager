package cz.muni.fi.pa165.icehockeymanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * A DTO for the {@link cz.muni.fi.pa165.icehockeymanager.entity.User} entity
 */
public record UserDto(
        @Size(max = 1024)
        @NotNull
        String email,

        @Size(max = 64)
        @NotNull
        String password,

        @Size(max = 16)
        @NotNull
        String role
) implements Serializable, UserDetails {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                return List.of(authority);
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public String getUsername() {
                return email;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}

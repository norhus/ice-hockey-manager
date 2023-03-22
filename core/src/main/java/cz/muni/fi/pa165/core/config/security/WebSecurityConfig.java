package cz.muni.fi.pa165.core.config.security;

import cz.muni.fi.pa165.model.shared.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenFilterComponent jwtTokenFilterComponent;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfig(JwtTokenFilterComponent jwtTokenFilterComponent, AuthenticationProvider authenticationProvider) {
        this.jwtTokenFilterComponent = jwtTokenFilterComponent;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/api/hockey-players/create", "/api/hockey-players/update").hasAnyRole(parseRole(Role.ROLE_ADMIN))
                        .requestMatchers("/api/**").hasAnyRole(parseRole(Role.ROLE_USER), parseRole(Role.ROLE_ADMIN))
                        .anyRequest().authenticated()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtTokenFilterComponent, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private String parseRole(Role role) {
        if (!role.name().contains("ROLE_")) {
            throw new IllegalStateException("Wrong format for user role enum!");
        }

        return role.name().split("_")[1];
    }
}

package cz.muni.fi.pa165.icehockeymanager.config.security;

import cz.muni.fi.pa165.icehockeymanager.shared.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenFilterComponent jwtTokenFilterComponent;

    @Autowired
    public WebSecurityConfig(JwtTokenFilterComponent jwtTokenFilterComponent) {
        this.jwtTokenFilterComponent = jwtTokenFilterComponent;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/users/*").permitAll()
                        .requestMatchers("/api/hockey-players/get-all").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenFilterComponent, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

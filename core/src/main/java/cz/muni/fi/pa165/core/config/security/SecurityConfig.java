package cz.muni.fi.pa165.core.config.security;

import cz.muni.fi.pa165.core.service.UserService;
import cz.muni.fi.pa165.model.shared.enums.Role;
import cz.muni.fi.pa165.model.shared.enums.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.POST,"/api/hockey-players").hasRole(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/hockey-players").hasRole(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/hockey-players").hasRole(Role.ROLE_ADMIN.name())

                        .requestMatchers(HttpMethod.POST,"/api/leagues").hasRole(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/teams").hasRole(Role.ROLE_ADMIN.name())

                        .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority(Scope.TEST_READ.label)
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority(Scope.TEST_WRITE.label)
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority(Scope.TEST_WRITE.label)
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Scope.TEST_WRITE.label)
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken)
        ;
        return http.build();
    }

    @Bean
    public OpaqueTokenIntrospector introspector() {
        return new CustomAuthoritiesOpaqueTokenIntrospector(userService);
    }
}

package cz.muni.fi.pa165.core.config.security;

import cz.muni.fi.pa165.model.shared.enums.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(x -> x
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
}

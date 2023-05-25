package cz.muni.fi.pa165.core.config.security;

import cz.muni.fi.pa165.core.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}")
    private String uri;
    private final UserService userService;

    public CustomAuthoritiesOpaqueTokenIntrospector(UserService userService) {
        this.userService = userService;
    }

    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OpaqueTokenIntrospector delegate = new NimbusOpaqueTokenIntrospector(uri, clientId, clientSecret);

        OAuth2AuthenticatedPrincipal principal = delegate.introspect(token);
        return new DefaultOAuth2AuthenticatedPrincipal(
                principal.getName(), principal.getAttributes(), extractAuthorities(principal));
    }

    private List<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
        List<String> scopesS = principal.getAttribute(OAuth2TokenIntrospectionClaimNames.SCOPE);
        if (scopesS == null) {
            scopesS = List.of();
        }

        var scopes = scopesS.stream().map(SimpleGrantedAuthority::new).toList();
        var authorities = userService.getAuthoritiesByEmail(principal.getName());

        return Stream.concat(authorities.stream(), scopes.stream()).collect(Collectors.toList());
    }
}

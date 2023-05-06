package cz.muni.fi.pa165.leaguetable.apiclient;

import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class TeamApiClient {

    public final WebClient coreClient;

    @Autowired
    public TeamApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public List<TeamDto> getTeamsByLeagueName(String leagueName, String token) {
        return coreClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .pathSegment("api", "teams", "find-by-league", leagueName).build())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                    .block();
    }
}

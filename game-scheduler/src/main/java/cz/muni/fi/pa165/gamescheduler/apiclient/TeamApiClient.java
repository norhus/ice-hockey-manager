package cz.muni.fi.pa165.gamescheduler.apiclient;

import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Component for communication with team endpoints
 */
@Component
public class TeamApiClient {

    public final WebClient coreClient;

    /**
     * Creates a TeamApiClient instance
     */
    @Autowired
    public TeamApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    /**
     * Fetches all teams from the specified league using endpoint from core
     *
     * @param leagueName String name of league
     * @param token String with the token
     * @return List of teams from the specified league
     */
    public List<TeamDto> getTeams(String leagueName, String token) {

        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "teams", "find-by-league", leagueName)
                        .build())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .block();
    }
}

package cz.muni.fi.pa165.leaguetable.apiclient;

import cz.muni.fi.pa165.leaguetable.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
public class TeamApiClient {

    public final WebClient coreClient;

    @Autowired
    public TeamApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public List<TeamDto> getTeamsByLeagueName(String leagueName) {
        List<TeamDto> teams;

        try {
            teams = coreClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .pathSegment("api", "teams", "find-by-league", leagueName).build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                    .block();

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException(String.format("Team with name %s does not exist", leagueName));
            } else {
                throw new RuntimeException("Unexpected Server Error", e);
            }
        }

        return teams;
    }
}

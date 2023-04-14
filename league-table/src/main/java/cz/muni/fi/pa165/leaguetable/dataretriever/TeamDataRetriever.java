package cz.muni.fi.pa165.leaguetable.dataretriever;

import cz.muni.fi.pa165.leaguetable.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamDataRetriever {

    public final WebClient coreClient;

    @Autowired
    public TeamDataRetriever(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public List<TeamDto> getTeams(String leagueName) throws ResourceNotFoundException {
        List<TeamDto> teams = new ArrayList<>();

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
            }
        }

        return teams;
    }
}

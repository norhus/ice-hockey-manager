package cz.muni.fi.pa165.leaguetable.dataretriever;

import cz.muni.fi.pa165.leaguetable.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeagueDataRetriever {

    public final WebClient coreClient;

    @Autowired
    public LeagueDataRetriever(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public LeagueDto getLeague(String leagueName) throws ResourceNotFoundException {
        LeagueDto league = null;

        try {
            league = coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "league", leagueName).build())
                    .retrieve()
                    .bodyToMono(LeagueDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException(String.format("League with name %s does not exist", leagueName));
            }
        }

        return league;
    }

    public List<LeagueDto> getLeagues() throws ResourceNotFoundException {
        List<LeagueDto> leagues = new ArrayList<>();

        try {
            leagues = coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "league" ,"get-all").build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException("There is no league");
            }
        }

        return leagues;
    }
}

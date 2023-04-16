package cz.muni.fi.pa165.leaguetable.apiclient;

import cz.muni.fi.pa165.leaguetable.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
public class LeagueApiClient {

    public final WebClient coreClient;

    @Autowired
    public LeagueApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public LeagueDto getLeagueByName(String leagueName) {
        LeagueDto league;

        try {
            league = coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "leagues", leagueName).build())
                    .retrieve()
                    .bodyToMono(LeagueDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException(String.format("League with name %s does not exist", leagueName));
            } else {
                throw new RuntimeException("Unexpected Server Error", e);
            }
        }

        return league;
    }

    public List<LeagueDto> getLeagues() {
        List<LeagueDto> leagues;

        try {
            leagues = coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "leagues").build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException("There is no league");
            } else {
                throw new RuntimeException("Unexpected Server Error", e);
            }
        }

        return leagues;
    }
}

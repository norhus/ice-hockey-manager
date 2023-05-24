package cz.muni.fi.pa165.gamescheduler.apiclient;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Component for communication with league endpoints
 */
@Component
public class LeagueApiClient {

    public final WebClient coreClient;

    /**
     * Creates a LeagueApiClient instance
     */
    @Autowired
    public LeagueApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    /**
     * Fetches all leagues using endpoint from core
     *
     * @param token String with the token
     * @return List of leagues
     */
    public List<LeagueDto> getLeagues(String token) {

        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "leagues").build())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {
                })
                .block();
    }
}
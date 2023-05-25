package cz.muni.fi.pa165.leaguetable.apiclient;

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

    @Autowired
    public LeagueApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    /**
     * Retrieves a league by its name using endpoint from core
     *
     * @param leagueName the name of the league to retrieve
     * @param token      the authentication token for the request
     * @return the LeagueDto representing the retrieved league
     */
    public LeagueDto getLeagueByName(String leagueName, String token) {
        return coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "leagues", leagueName).build())
                    .headers(h -> h.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(LeagueDto.class)
                    .block();
    }

    /**
     * Retrieves a list of leagues using endpoint from core
     *
     * @param token the authentication token for the request
     * @return a List of LeagueDto representing the retrieved leagues
     */
    public List<LeagueDto> getLeagues(String token) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "leagues").build())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {})
                .block();
    }
}

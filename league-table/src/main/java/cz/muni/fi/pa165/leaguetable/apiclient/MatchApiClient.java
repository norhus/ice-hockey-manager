package cz.muni.fi.pa165.leaguetable.apiclient;

import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Component for communication with match endpoints
 */
@Component
public class MatchApiClient {

    public final WebClient coreClient;

    @Autowired
    public MatchApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    /**
     * Retrieves a list of matches by league name using endpoint from core
     *
     * @param leagueName the name of the league for which to retrieve matches.
     * @param token      the authentication token for the request.
     * @return a List of MatchDto representing the retrieved matches.
     */
    public List<MatchDto> getMatchesByLeagueName(String leagueName, String token) {
        return coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "matches", "find-played-matches", leagueName).build())
                    .headers(h -> h.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MatchDto>>() {})
                    .block();
    }
}

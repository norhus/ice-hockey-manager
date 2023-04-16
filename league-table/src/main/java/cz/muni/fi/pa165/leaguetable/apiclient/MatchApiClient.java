package cz.muni.fi.pa165.leaguetable.apiclient;

import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchApiClient {

    public final WebClient coreClient;

    @Autowired
    public MatchApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public List<MatchDto> getMatchesByLeagueName(String leagueName) {
        List<MatchDto> matches = new ArrayList<>();

        try {
            matches = coreClient.get()
                    .uri(uriBuilder -> uriBuilder.
                            pathSegment("api", "matches", "find-played-matches", leagueName).build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MatchDto>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                return matches;
            } else {
                throw new RuntimeException("Unexpected Server Error", e);
            }
        }

        return matches;
    }
}

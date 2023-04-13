package cz.muni.fi.pa165.leaguetable.dataretriever;

import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class MatchDataRetriever {

    public final WebClient coreClient;

    @Autowired
    public MatchDataRetriever(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public List<MatchDto> getMatches(String leagueName) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "match", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MatchDto>>() {})
                .block();
    }
}

package cz.muni.fi.pa165.leaguetable.dataretriever;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class LeagueDataRetriever {

    public final WebClient coreClient;

    @Autowired
    public LeagueDataRetriever(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public LeagueDto getLeague(String leagueName) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league", leagueName).build())
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .block();
    }

    public List<LeagueDto> getLeagues() {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league" ,"get-all").build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {})
                .block();
    }
}

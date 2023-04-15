package cz.muni.fi.pa165.gamescheduler.apiclient;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class LeagueApiClient {

    public final WebClient coreClient;

    @Autowired
    public LeagueApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public List<LeagueDto> getLeagues() {

        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "leagues").build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {
                })
                .block();
    }
}
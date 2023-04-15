package cz.muni.fi.pa165.gamescheduler.apiclient;

import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class MatchApiClient {

    public final WebClient coreClient;

    @Autowired
    public MatchApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public MatchDto postMatch(Instant gameDate, Pair<TeamDto, TeamDto> matchTeams) {

        return coreClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "matches").build())
                .body(Mono.just(new MatchDto(0L, gameDate,
                        null, null, matchTeams.getValue0(), matchTeams.getValue1())), MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block();
    }
}

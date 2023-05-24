package cz.muni.fi.pa165.gamescheduler.apiclient;

import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Component for communication with match endpoints
 */
@Component
public class MatchApiClient {

    public final WebClient coreClient;

    /**
     * Creates a MatchApiClient instance
     */
    @Autowired
    public MatchApiClient(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    /**
     * Post a new match using endpoint from core
     *
     * @param gameDate Instant date of match
     * @param matchTeams Pair of teams playing in the match
     * @param token String with the token
     * @return Created match
     */
    public MatchDto postMatch(Instant gameDate, Pair<TeamDto, TeamDto> matchTeams, String token) {

        return coreClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "matches").build())
                .headers(h -> h.setBearerAuth(token))
                .body(Mono.just(new MatchDto(0L, gameDate,
                        null, null, matchTeams.getValue0(), matchTeams.getValue1())), MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block();
    }
}

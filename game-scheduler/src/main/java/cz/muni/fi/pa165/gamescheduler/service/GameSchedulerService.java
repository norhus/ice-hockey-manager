package cz.muni.fi.pa165.gamescheduler.service;

import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameSchedulerService {

    public final WebClient client = WebClient.create("http://localhost:8080/");

    public GameSchedulerDto generate(String leagueName) {

        List<TeamDto> teams = client.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "team", "find-by-league")
                        .queryParam("league", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .block();

        List<MatchDto> matches = new ArrayList<>();
        matches.add(client.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "match", "create").build())
                .body(Mono.just(new MatchDto(0L, Instant.parse("2023-04-27T19:00:00.715Z"),
                        null, null, teams.get(0), teams.get(1))), MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block());

        matches.add(client.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "match", "create").build())
                .body(Mono.just(new MatchDto(0L, Instant.parse("2023-04-27T19:00:00.715Z"),
                        null, null, teams.get(2), teams.get(3))), MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block());

        matches.add(client.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "match", "create").build())
                .body(Mono.just(new MatchDto(0L, Instant.parse("2023-04-29T19:00:00.715Z"),
                        null, null, teams.get(4), teams.get(0))), MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block());

        return new GameSchedulerDto(Instant.now(), teams, matches);
    }
}

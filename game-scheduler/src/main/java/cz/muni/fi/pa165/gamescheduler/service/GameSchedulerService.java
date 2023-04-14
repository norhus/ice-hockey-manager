package cz.muni.fi.pa165.gamescheduler.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameSchedulerService {

    public final WebClient coreClient;

    @Autowired
    public GameSchedulerService(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public GameSchedulerDto generate(String leagueName) {

        List<TeamDto> teams = coreClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "teams", "find-by-league", leagueName)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .block();

        int numOfTeams = teams.size();
        boolean ghost = false;
        if (numOfTeams % 2 != 0) {
            numOfTeams++;
            ghost = true;
        }
        List<List<Pair<TeamDto, TeamDto>>> fixtures = new ArrayList<>();

        for (int i = 0; i < numOfTeams - 1; i++) {
            List<Pair<TeamDto, TeamDto>> fixture = new ArrayList<>();
            for (int j = 0; j < numOfTeams / 2; j++) {
                int home = (i + j) % (numOfTeams - 1);
                int away = (numOfTeams - 1 - j + i) % (numOfTeams - 1);
                if (j == 0) {
                    away = numOfTeams - 1;
                }
                if (!(j == 0 && ghost)) {
                    fixture.add(new Pair<>(teams.get(home), teams.get(away)));
                }
            }
            fixtures.add(fixture);
        }

        List<List<Pair<TeamDto, TeamDto>>> interleaved = new ArrayList<>();

        int even = 0;
        int odd = (numOfTeams / 2);
        for (int i = 0; i < fixtures.size(); i++) {
            if (i % 2 == 0) {
                interleaved.add(fixtures.get(even++));
            } else {
                interleaved.add(fixtures.get(odd++));
            }
        }

        fixtures = interleaved;

        if (!ghost) {
            for (int i = 0; i < fixtures.size(); i++) {
                if (i % 2 == 1) {
                    Pair<TeamDto, TeamDto> matchTeams = fixtures.get(i).get(0);
                    fixtures.get(i).set(0, new Pair<>(matchTeams.getValue1(), matchTeams.getValue0()));
                }
            }
        }

        List<List<Pair<TeamDto, TeamDto>>> reverseFixtures = new ArrayList<>();
        for(List<Pair<TeamDto, TeamDto>> fixture: fixtures){
            List<Pair<TeamDto, TeamDto>> reverseFixture = new ArrayList<>();
            for(Pair<TeamDto, TeamDto> matchTeams: fixture){
                reverseFixture.add(new Pair<>(matchTeams.getValue1(), matchTeams.getValue0()));
            }
            reverseFixtures.add(reverseFixture);
        }
        fixtures.addAll(reverseFixtures);

        List<MatchDto> matches = new ArrayList<>();
        Instant gameDate = Instant.now();

        for (List<Pair<TeamDto, TeamDto>> fixture: fixtures) {
            gameDate = gameDate.plus(2, ChronoUnit.DAYS);
            for (Pair<TeamDto, TeamDto> matchTeams : fixture) {
                matches.add(coreClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "matches").build())
                .body(Mono.just(new MatchDto(0L, gameDate,
                        null, null, matchTeams.getValue0(), matchTeams.getValue1())), MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block());
            }
        }

        return new GameSchedulerDto(Instant.now(), teams, matches);
    }

    public List<GameSchedulerDto> generateAll() {

        List<LeagueDto> leagues = coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "leagues").build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {})
                .block();

        return leagues.stream().map(l -> generate(l.name())).collect(Collectors.toList());
    }
}

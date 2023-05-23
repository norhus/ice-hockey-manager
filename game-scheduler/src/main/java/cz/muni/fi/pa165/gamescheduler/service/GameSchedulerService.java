package cz.muni.fi.pa165.gamescheduler.service;

import cz.muni.fi.pa165.gamescheduler.apiclient.LeagueApiClient;
import cz.muni.fi.pa165.gamescheduler.apiclient.MatchApiClient;
import cz.muni.fi.pa165.gamescheduler.apiclient.TeamApiClient;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameSchedulerService {

    private final TeamApiClient teamApiClient;
    private final LeagueApiClient leagueApiClient;
    private final MatchApiClient matchApiClient;

    @Autowired
    public GameSchedulerService(TeamApiClient teamApiClient, LeagueApiClient leagueApiClient,
                                MatchApiClient matchApiClient) {
        this.teamApiClient = teamApiClient;
        this.leagueApiClient = leagueApiClient;
        this.matchApiClient = matchApiClient;
    }

    public GameSchedulerDto generate(String leagueName, String token) {

        List<TeamDto> teams = teamApiClient.getTeams(leagueName, token);
        int numOfTeams = teams.size();
        boolean ghost = false;
        if (numOfTeams % 2 != 0) {
            numOfTeams++;
            ghost = true;
        }
        List<List<Pair<TeamDto, TeamDto>>> fixtures = createFixtures(teams, numOfTeams, ghost);

        fixtures = interleaveAndMix(numOfTeams, ghost, fixtures);

        fixtures.addAll(createReverseFixtures(fixtures));

        List<MatchDto> matches = new ArrayList<>();
        Instant gameDate = Instant.now();

        for (List<Pair<TeamDto, TeamDto>> fixture: fixtures) {
            gameDate = gameDate.plus(2, ChronoUnit.DAYS);
            for (Pair<TeamDto, TeamDto> matchTeams : fixture) {
                matches.add(matchApiClient.postMatch(gameDate, matchTeams, token));
            }
        }

        return new GameSchedulerDto(Instant.now(), teams, matches);
    }

    private static List<List<Pair<TeamDto, TeamDto>>> createFixtures(List<TeamDto> teams, int numOfTeams, boolean ghost) {
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
        return fixtures;
    }

    private static List<List<Pair<TeamDto, TeamDto>>> interleaveAndMix(int numOfTeams, boolean ghost, List<List<Pair<TeamDto, TeamDto>>> fixtures) {
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

        if (!ghost) {
            for (int i = 0; i < interleaved.size(); i++) {
                if (i % 2 == 1) {
                    Pair<TeamDto, TeamDto> matchTeams = interleaved.get(i).get(0);
                    interleaved.get(i).set(0, new Pair<>(matchTeams.getValue1(), matchTeams.getValue0()));
                }
            }
        }
        return interleaved;
    }

    private static List<List<Pair<TeamDto, TeamDto>>> createReverseFixtures(List<List<Pair<TeamDto, TeamDto>>> fixtures) {
        List<List<Pair<TeamDto, TeamDto>>> reverseFixtures = new ArrayList<>();
        for(List<Pair<TeamDto, TeamDto>> fixture: fixtures){
            List<Pair<TeamDto, TeamDto>> reverseFixture = new ArrayList<>();
            for(Pair<TeamDto, TeamDto> matchTeams: fixture){
                reverseFixture.add(new Pair<>(matchTeams.getValue1(), matchTeams.getValue0()));
            }
            reverseFixtures.add(reverseFixture);
        }
        return reverseFixtures;
    }

    public List<GameSchedulerDto> generateAll(String token) {

        List<LeagueDto> leagues = leagueApiClient.getLeagues(token);

        return leagues.stream().map(l -> generate(l.name(), token)).collect(Collectors.toList());
    }
}

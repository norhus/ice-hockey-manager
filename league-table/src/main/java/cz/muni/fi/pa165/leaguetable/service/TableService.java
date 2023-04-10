package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class TableService {

    public final WebClient coreClient;

    @Autowired
    public TableService(WebClient coreClient) {
        this.coreClient = coreClient;
    }

    public TableDto findByLeague(String leagueName) {
        List<TeamDto> teams = getTeams(leagueName);
        List<MatchDto> matches = getMatches(leagueName);

        LeagueDto league = coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league", leagueName).build())
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .block();

        return makeTable(league, teams, matches);
    }


    public List<TableDto> findAll() {
        List<TableDto> tables = new ArrayList<>();

        List<LeagueDto> leagues = coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league" ,"get-all").build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LeagueDto>>() {})
                .block();

        for (var league : leagues) {
            List<TeamDto> teams = getTeams(league.name());
            List<TableRowDto> rows = makeRows(teams);

            tables.add(new TableDto(league, rows));
        }

        return tables;
    }

    private List<TeamDto> getTeams(String leagueName) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("api", "team", "find-by-league", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .block();
    }

    private List<MatchDto> getMatches(String leagueName) {
        return coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "match", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MatchDto>>() {
                })
                .block();
    }

    private static List<TableRowDto> makeRows(List<TeamDto> teams) {
        return teams.stream()
                .map(t -> new TableRowDto(t.name()))
                .toList();
    }

    private TableDto makeTable(LeagueDto league, List<TeamDto> teams, List<MatchDto> matches) {
        Map<String, TableRowDto> tableRowMap = new HashMap<>();

        for (TeamDto team : teams) {
            // initialize the table row for each team
            TableRowDto row = new TableRowDto(team.name());
            // map the team name to the table row in the map
            tableRowMap.put(team.name(), row);
        }

        // count statistics of matches for each team in table
        for (MatchDto match : matches) {
            var homeTeam = match.homeTeam().name();
            var awayTeam = match.awayTeam().name();
            var homeTeamGoals = match.homeGoals();
            var awayTeamGoals = match.awayGoals();

            var homeTableRow = tableRowMap.get(homeTeam);
            var awayTableRow = tableRowMap.get(awayTeam);

            homeTableRow = updateTeamStats(homeTeamGoals, awayTeamGoals, homeTableRow);
            awayTableRow = updateTeamStats(awayTeamGoals, homeTeamGoals, awayTableRow);

            tableRowMap.put(homeTeam, homeTableRow);
            tableRowMap.put(awayTeam, awayTableRow);
        }

        List<TableRowDto> rankedTableRows  = rankTeams(tableRowMap.values().stream().toList());

        return new TableDto(league, rankedTableRows);
    }

    private TableRowDto updateTeamStats(int goalsFor, int goalsAgainst, TableRowDto team) {
        team = new TableRowDto(
                team.teamName(),
                0,
                team.matchesPlayed() + 1,
                goalsFor > goalsAgainst ? team.won() + 1 : team.won(),
                goalsFor == goalsAgainst ? team.drawn() + 1 : team.drawn(),
                goalsFor < goalsAgainst ? team.lost() + 1 : team.lost(),
                team.goalsFor() + goalsFor,
                team.goalsAgainst() + goalsAgainst,
                goalsFor > goalsAgainst ? 3 : (goalsFor < goalsAgainst ? 0 : 1)
        );

        return team;
    }

    private List<TableRowDto> rankTeams(List<TableRowDto> rows) {
        List<TableRowDto> sortedTableRows = rows.stream()
                .sorted(Comparator.comparing(TableRowDto::points)
                        .thenComparing(row -> row.goalsFor() - row.goalsAgainst())
                        .thenComparing(TableRowDto::goalsFor)
                        .reversed())
                .toList();

        List<TableRowDto> rankedTableRows  = new ArrayList<>();
        int rank = 1;

        for (TableRowDto row : sortedTableRows) {
            rankedTableRows.add(new TableRowDto(
                    row.teamName(),
                    rank,
                    row.matchesPlayed(),
                    row.won(),
                    row.drawn(),
                    row.lost(),
                    row.goalsFor(),
                    row.goalsAgainst(),
                    row.points()));
            rank++;
        }

        return rankedTableRows;
    }
}

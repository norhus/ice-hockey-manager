package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.leaguetable.apiclient.LeagueApiClient;
import cz.muni.fi.pa165.leaguetable.apiclient.MatchApiClient;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TableService {

    private final LeagueApiClient leagueApiClient;
    private final MatchApiClient matchApiClient;

    @Autowired
    public TableService(LeagueApiClient leagueApiClient, MatchApiClient matchApiClient) {
        this.leagueApiClient = leagueApiClient;
        this.matchApiClient = matchApiClient;
    }

    public TableDto findByLeague(String leagueName, String token) {
        LeagueDto league = leagueApiClient.getLeagueByName(leagueName, token);
        List<MatchDto> matches = matchApiClient.getMatchesByLeagueName(leagueName, token);

        return makeTable(league, league.teams(), matches);
    }

    public List<TableDto> findAll(String token) {
        List<TableDto> tables = new ArrayList<>();
        List<LeagueDto> leagues = leagueApiClient.getLeagues(token);

        for (var league : leagues) {
            tables.add(findByLeague(league.name(), token));
        }

        return tables;
    }

    private TableDto makeTable(LeagueDto league, Set<TeamDto> teams, List<MatchDto> matches) {
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

package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.leaguetable.dataretriever.LeagueDataRetriever;
import cz.muni.fi.pa165.leaguetable.dataretriever.MatchDataRetriever;
import cz.muni.fi.pa165.leaguetable.dataretriever.TeamDataRetriever;
import cz.muni.fi.pa165.leaguetable.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableService {

    private final LeagueDataRetriever leagueDataRetriever;
    private final TeamDataRetriever teamDataRetriever;
    private final MatchDataRetriever matchDataRetriever;

    @Autowired
    public TableService(LeagueDataRetriever leagueDataRetriever, TeamDataRetriever teamDataRetriever, MatchDataRetriever matchDataRetriever) {
        this.leagueDataRetriever = leagueDataRetriever;
        this.teamDataRetriever = teamDataRetriever;
        this.matchDataRetriever = matchDataRetriever;
    }

    public TableDto findByLeague(String leagueName) throws ResourceNotFoundException {
        LeagueDto league = leagueDataRetriever.getLeague(leagueName);
        List<TeamDto> teams = teamDataRetriever.getTeams(leagueName);
        List<MatchDto> matches = matchDataRetriever.getMatches(leagueName);

        return makeTable(league, teams, matches);
    }

    public List<TableDto> findAll() throws ResourceNotFoundException {
        List<TableDto> tables = new ArrayList<>();
        List<LeagueDto> leagues = leagueDataRetriever.getLeagues();

        for (var league : leagues) {
            tables.add(findByLeague(league.name()));
        }

        return tables;
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

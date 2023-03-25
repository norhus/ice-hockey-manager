package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
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

        LeagueDto league = coreClient.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league", leagueName).build())
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .block();

        List<TableRowDto> rows = makeRows(teams);


        return new TableDto(league, rows);
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
                        .pathSegment("api", "team", "find-by-league")
                        .queryParam("league", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .block();
    }

    private static List<TableRowDto> makeRows(List<TeamDto> teams) {
        return teams.stream()
                .map(t -> {
                    TableRowDto row = new TableRowDto();
                    row.setTeamName(t.name());
                    return row;
                }).toList();
    }
}

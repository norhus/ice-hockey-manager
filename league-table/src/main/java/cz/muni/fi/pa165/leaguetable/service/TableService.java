package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class TableService {

    public final WebClient client = WebClient.create("http://localhost:8080/");

    public TableDto findByLeague(String leagueName) {

        List<TeamDto> teams = client.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("api", "team", "find-by-league")
                        .queryParam("league", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .block();

        LeagueDto league = client.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league", leagueName).build())
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .block();

        List<TableRowDto> rows = teams.stream()
                .map(t -> {
                    TableRowDto row = new TableRowDto();
                    row.setTeamName(t.name());
                    return row;
                }).toList();


        return new TableDto(league, rows);
    }
}

package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableService {

    public final WebClient client = WebClient.create("http://localhost:8080/");

    public TableDto findByLeague(String leagueName) {
        TableDto table = new TableDto();

        LeagueDto league = client.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "league", leagueName).build())
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .block();

        List<MatchDto> matches = client.get()
                .uri(uriBuilder -> uriBuilder.
                        pathSegment("api", "match", leagueName).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MatchDto>>() {})
                .block();

        List<TableRowDto> rows = new ArrayList<>();

        for (var m : matches) {
            TableRowDto row1 = new TableRowDto();
            row1.setTeamName(m.homeTeam().name());
            TableRowDto row2 = new TableRowDto();
            row2.setTeamName(m.awayTeam().name());

            rows.add(row1);
            rows.add(row2);
        }

        var rowsWithoutDuplicate = rows.stream().distinct().collect(Collectors.toList());

        table.setLeague(league);
        table.setTeams(rowsWithoutDuplicate);

        return table;
    }
}

package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.leaguetable.apiclient.LeagueApiClient;
import cz.muni.fi.pa165.leaguetable.apiclient.MatchApiClient;
import cz.muni.fi.pa165.leaguetable.apiclient.TeamApiClient;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock
    private LeagueApiClient leagueApiClient;
    @Mock
    private TeamApiClient teamApiClient;
    @Mock
    private MatchApiClient matchApiClient;
    @InjectMocks
    private TableService tableService;

    LeagueDto firstLeague = new LeagueDto(1L, "NHL", null);
    LeagueDto secondLeague = new LeagueDto(2L, "Slovan", null);
    TeamDto team1 = new TeamDto(1L, "TestTeam1", null, firstLeague, null);
    TeamDto team2 = new TeamDto(2L, "TestTeam2", null, firstLeague, null);
    TeamDto team3 = new TeamDto(3L, "TestTeam3", null, secondLeague, null);
    TeamDto team4 = new TeamDto(4L, "TestTeam4", null, secondLeague, null);
    List<TeamDto> firstLeagueTeams = List.of(team1, team2);
    List<TeamDto> secondLeagueTeams = List.of(team3, team4);
    List<MatchDto> firstLeagueMatches = List.of(
            new MatchDto(1L, Instant.now(), 1, 1, team1, team2),
            new MatchDto(2L, Instant.now().plus(2, ChronoUnit.DAYS), 2, 3, team2, team1)
    );
    List<MatchDto> secondLeagueMatches = List.of(
            new MatchDto(3L, Instant.now().plus(4, ChronoUnit.DAYS), 0, 0, team3, team4),
            new MatchDto(4L, Instant.now().plus(6, ChronoUnit.DAYS), 0, 1, team4, team3)
    );

    @Test
    void findByLeague() {
        when(leagueApiClient.getLeagueByName(any())).thenReturn(firstLeague);
        when(teamApiClient.getTeamsByLeagueName(any())).thenReturn(List.of(team1, team2));
        when(matchApiClient.getMatchesByLeagueName(any())).thenReturn(firstLeagueMatches);

        TableDto tableDto = tableService.findByLeague(firstLeague.name());

        assertThat(tableDto.teams()).hasSize(2);
        assertThat(tableDto.league()).isEqualTo(firstLeague);

        verify(leagueApiClient, times(1)).getLeagueByName(any());
        verify(teamApiClient, times(1)).getTeamsByLeagueName(any());
        verify(matchApiClient, times(1)).getMatchesByLeagueName(any());
    }

    @Test
    void findAll() {
        when(leagueApiClient.getLeagues()).thenReturn(List.of(firstLeague, secondLeague));
        when(leagueApiClient.getLeagueByName(any()))
                .thenReturn(firstLeague)
                .thenReturn(secondLeague);
        when(teamApiClient.getTeamsByLeagueName(any()))
                .thenReturn(firstLeagueTeams)
                .thenReturn(secondLeagueTeams);
        when(matchApiClient.getMatchesByLeagueName(any()))
                .thenReturn(firstLeagueMatches)
                .thenReturn(secondLeagueMatches);

        List<TableDto> tableDtoList = tableService.findAll();

        assertThat(tableDtoList).hasSize(2);
        assertThat(tableDtoList.get(0).league()).isEqualTo(firstLeague);
        assertThat(tableDtoList.get(1).league()).isEqualTo(secondLeague);
        assertThat(tableDtoList.get(0).teams().get(0).teamName()).isEqualTo(team1.name());
        assertThat(tableDtoList.get(0).teams().get(1).teamName()).isEqualTo(team2.name());
        assertThat(tableDtoList.get(1).teams().get(0).teamName()).isEqualTo(team3.name());
        assertThat(tableDtoList.get(1).teams().get(1).teamName()).isEqualTo(team4.name());

        verify(leagueApiClient, times(2)).getLeagueByName(any());
        verify(teamApiClient, times(2)).getTeamsByLeagueName(any());
        verify(matchApiClient, times(2)).getMatchesByLeagueName(any());
    }
}
package cz.muni.fi.pa165.gamescheduler.service;

import cz.muni.fi.pa165.gamescheduler.apiclient.LeagueApiClient;
import cz.muni.fi.pa165.gamescheduler.apiclient.MatchApiClient;
import cz.muni.fi.pa165.gamescheduler.apiclient.TeamApiClient;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameSchedulerServiceTest {

    @Mock
    TeamApiClient teamApiClient;

    @Mock
    LeagueApiClient leagueApiClient;

    @Mock
    MatchApiClient matchApiClient;

    @InjectMocks
    private GameSchedulerService gameSchedulerService;

    String firstLeagueName = "League1";
    String secondLeagueName = "League2";
    List<TeamDto> firstLeagueTeams = new ArrayList<>();
    List<TeamDto> secondLeagueTeams = new ArrayList<>();
    List<MatchDto> firstLeagueMatches = new ArrayList<>();
    List<MatchDto> secondLeagueMatches = new ArrayList<>();

    @BeforeEach
    void setUp() {
        TeamDto team1 = new TeamDto(1L, "TestTeam1", null, null, null);
        TeamDto team2 = new TeamDto(2L, "TestTeam2", null, null, null);
        TeamDto team3 = new TeamDto(3L, "TestTeam3", null, null, null);
        TeamDto team4 = new TeamDto(4L, "TestTeam4", null, null, null);

        MatchDto match1 = new MatchDto(1L, Instant.now(), null, null, team1, team2);
        MatchDto match2 = new MatchDto(2L, Instant.now().plus(2, ChronoUnit.DAYS), null, null, team2, team1);
        MatchDto match3 = new MatchDto(3L, Instant.now().plus(4, ChronoUnit.DAYS), null, null, team3, team4);
        MatchDto match4 = new MatchDto(4L, Instant.now().plus(6, ChronoUnit.DAYS), null, null, team4, team3);

        firstLeagueTeams.add(team1);
        firstLeagueTeams.add(team2);
        secondLeagueTeams.add(team3);
        secondLeagueTeams.add(team4);

        firstLeagueMatches.add(match1);
        firstLeagueMatches.add(match2);
        secondLeagueMatches.add(match3);
        secondLeagueMatches.add(match4);
    }

    @Test
    void generate() {
        when(teamApiClient.getTeams(any(), any())).thenReturn(firstLeagueTeams);
        when(matchApiClient.postMatch(any(), any(), any()))
                .thenReturn(firstLeagueMatches.get(0))
                .thenReturn(firstLeagueMatches.get(1));

        GameSchedulerDto gameScheduler = gameSchedulerService.generate(firstLeagueName, "some_token");

        assertThat(gameScheduler.teams()).hasSize(2);
        assertThat(gameScheduler.matches()).hasSize(2);

        verify(teamApiClient, times(1)).getTeams(firstLeagueName, "some_token");
        verify(matchApiClient, times(2)).postMatch(any(), any(), any());
    }


    @Test
    void generateWithOneTeam() {
        TeamDto team1 = new TeamDto(1L, "TestTeam1", null, null, null);
        List<TeamDto> teams = new ArrayList<>();

        teams.add(team1);

        when(teamApiClient.getTeams(any(), any())).thenReturn(teams);
        when(matchApiClient.postMatch(any(), any(), any()))
                .thenReturn(any());

        GameSchedulerDto gameSchedulerDto = gameSchedulerService.generate(firstLeagueName, any());

        assertThat(gameSchedulerDto.teams()).hasSize(1);
        assertThat(gameSchedulerDto.matches()).isEmpty();

        verify(teamApiClient, times(1)).getTeams(any(), any());
        verify(matchApiClient, times(0)).postMatch(any(), any(), any());
    }
    @Test
    void generateAll() {
        List<LeagueDto> leagues = new ArrayList<>();
        LeagueDto league1 = new LeagueDto(1L, firstLeagueName, new HashSet<>(firstLeagueTeams));
        LeagueDto league2 = new LeagueDto(2L, secondLeagueName, new HashSet<>(secondLeagueTeams));

        leagues.add(league1);
        leagues.add(league2);

        when(leagueApiClient.getLeagues(any())).thenReturn(leagues);
        when(teamApiClient.getTeams(any(), any()))
                .thenReturn(firstLeagueTeams)
                .thenReturn(secondLeagueTeams);
        when(matchApiClient.postMatch(any(), any(), any()))
                .thenReturn(firstLeagueMatches.get(0))
                .thenReturn(firstLeagueMatches.get(1))
                .thenReturn(secondLeagueMatches.get(0))
                .thenReturn(secondLeagueMatches.get(1));

        List<GameSchedulerDto> gameSchedulers = gameSchedulerService.generateAll("some_token");

        assertThat(gameSchedulers).hasSize(2);
        assertThat(gameSchedulers.get(0).teams()).hasSize(2);
        assertThat(gameSchedulers.get(1).teams()).hasSize(2);
        assertThat(gameSchedulers.get(0).matches()).hasSize(2);
        assertThat(gameSchedulers.get(1).matches()).hasSize(2);

        verify(leagueApiClient, times(1)).getLeagues(any());
        verify(teamApiClient, times(2)).getTeams(any(), any());
        verify(matchApiClient, times(4)).postMatch(any(), any(), any());
    }
}

package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.entity.Match;
import cz.muni.fi.pa165.core.entity.Team;
import cz.muni.fi.pa165.core.mapper.MatchMapper;
import cz.muni.fi.pa165.core.repository.MatchRepository;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MatchServiceTests {

    @InjectMocks
    private MatchService matchService;

    @MockBean
    private MatchRepository matchRepository;

    @Autowired
    private MatchMapper matchMapper;

    League league;
    Team team1;
    Team team2;
    Match match1;
    Match match2;
    LeagueDto mockLeague = new LeagueDto(1L, "TIPOS Extraliga", null);
    TeamDto mockHomeTeam = new TeamDto(2L, "Kosice", null, mockLeague, new HashSet<>());
    TeamDto mockAwayTeam = new TeamDto(3L, "Banska Bystrica", null, mockLeague, new HashSet<>());
    MatchDto expectedMatchDto1 = new MatchDto(1L, Instant.parse("2023-04-10T19:10:25.00Z"),
            1, 1, mockHomeTeam, mockAwayTeam);
    MatchDto expectedMatchDto2 = new MatchDto(2L, Instant.parse("2023-07-10T19:10:25.00Z"),
            null, null, mockHomeTeam, mockAwayTeam);

    @BeforeEach
    void setUp() {
        matchService = new MatchService(matchRepository, matchMapper);
        league = new League();
        league.setId(1L);
        league.setName("TIPOS Extraliga");
        team1 = new Team();
        team1.setId(2L);
        team1.setName("Kosice");
        team1.setLeague(league);
        team2 = new Team();
        team2.setId(3L);
        team2.setName("Banska Bystrica");
        team2.setLeague(league);
        match1 = new Match();
        match1.setId(1L);
        match1.setDateOfMatch(Instant.parse("2023-04-10T19:10:25.00Z"));
        match1.setHomeGoals(1);
        match1.setAwayGoals(1);
        match1.setHomeTeam(team1);
        match1.setAwayTeam(team2);
        match2 = new Match();
        match2.setId(2L);
        match2.setDateOfMatch(Instant.parse("2023-07-10T19:10:25.00Z"));
        match2.setHomeTeam(team1);
        match2.setAwayTeam(team2);
    }

    @Test
    void findAll() {
        when(matchRepository.findAll()).thenReturn(List.of(match1, match2));

        List<MatchDto> matchDtos = matchService.findAll();

        assertEquals(matchDtos, List.of(expectedMatchDto1, expectedMatchDto2));
        verify(matchRepository, times(1)).findAll();
    }

    @Test
    void findByLeagueNameValid() {
        when(matchRepository.findByHomeTeamLeagueName("TIPOS Extraliga")).thenReturn(List.of(match1, match2));

        List<MatchDto> matchDtos = matchService.findByLeagueName("TIPOS Extraliga");

        assertEquals(matchDtos, List.of(expectedMatchDto1, expectedMatchDto2));
        verify(matchRepository, times(1)).findByHomeTeamLeagueName("TIPOS Extraliga");
    }

    @Test
    void findByLeagueNameInvalid() {
        when(matchRepository.findByHomeTeamLeagueName("superliga")).thenReturn(List.of());

        List<MatchDto> matchDtos = matchService.findByLeagueName("superliga");

        assertEquals(matchDtos, List.of());
        verify(matchRepository, times(1)).findByHomeTeamLeagueName("superliga");
    }

    @Test
    void createMatchValid() {
        when(matchRepository.save(any(Match.class))).thenReturn(match2);

        MatchDto matchDto = matchService.create(expectedMatchDto2);

        assertEquals(matchDto, expectedMatchDto2);
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void updateMatchValid() {
        when(matchRepository.findById(match1.getId())).thenReturn(Optional.of(match1));
        when(matchRepository.save(match1)).thenReturn(match1);

        MatchDto matchDto = matchService.update(expectedMatchDto1);

        assertEquals(matchDto, expectedMatchDto1);
        verify(matchRepository, times(1)).findById(match1.getId());
        verify(matchRepository, times(1)).save(match1);
    }

    @Test
    void updateMatchInvalid() {
        when(matchRepository.findById(match1.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> matchService.update(expectedMatchDto1));
        verify(matchRepository, times(1)).findById(match1.getId());
    }

    @Test
    void findUnplayedMatchesBeforeNowValid() {
        when(matchRepository.findUnplayedMatchesBeforeNow()).thenReturn(List.of(match2));

        List<MatchDto> matchDtos = matchService.findUnplayedMatchesBeforeNow();

        assertEquals(matchDtos, List.of(expectedMatchDto2));
        verify(matchRepository, times(1)).findUnplayedMatchesBeforeNow();
    }

    @Test
    void playUnplayedMatchesValid() {
        when(matchRepository.findUnplayedMatchesBeforeNow()).thenReturn(List.of(match2));
        when(matchRepository.findById(match2.getId())).thenReturn(Optional.of(match2));
        when(matchRepository.save(match2)).thenReturn(match2);

        List<MatchDto> matchDtos = matchService.playUnplayedMatches();

        for (MatchDto matchDto: matchDtos) {
            assertNotNull(matchDto.homeGoals());
            assertNotNull(matchDto.awayGoals());
        }
    }
}

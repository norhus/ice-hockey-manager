package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.entity.Team;
import cz.muni.fi.pa165.core.mapper.TeamMapper;
import cz.muni.fi.pa165.core.repository.HockeyPlayerRepository;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import cz.muni.fi.pa165.core.repository.TeamRepository;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeamServiceTests {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private HockeyPlayerRepository hockeyPlayerRepository;

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private UserService userService;

    @Autowired
    private TeamMapper teamMapper;

    private League league1;
    private League league2;
    private Team team1;
    private Team team2;
    private LeagueDto leagueDto;
    private TeamDto expectedTeamDto1;
    private TeamDto expectedTeamDto2;
    private List<HockeyPlayer> hockeyPlayers;

    @BeforeEach
    void setUp () {
        teamService = new TeamService(teamRepository, teamMapper, hockeyPlayerRepository, leagueRepository, userService);

        hockeyPlayers = new ArrayList<>();
        HockeyPlayer hockeyPlayer1 = new HockeyPlayer();
        hockeyPlayer1.setId(1L);
        hockeyPlayer1.setFirstName("Miroslav");
        hockeyPlayer1.setLastName("Satan");
        hockeyPlayers.add(hockeyPlayer1);

        league1 = new League();
        league1.setId(1L);
        league1.setName("TIPOS Extraliga");

        league2 = new League();
        league2.setId(1L);
        league2.setName("NHL");

        team1 = new Team();
        team1.setId(1L);
        team1.setName("Kosice");
        team1.setLeague(league1);
        team1.setHockeyPlayers(new HashSet<>(hockeyPlayers));

        team2 = new Team();
        team2.setId(3L);
        team2.setName("Banska Bystrica");
        team2.setLeague(league1);

        leagueDto = new LeagueDto(1L, "TIPOS Extraliga", null);
        HockeyPlayerDto hockeyPlayerDto = new HockeyPlayerDto(1L, "Miroslav", "Satan", null, null, null, null, null , null, null, null, null);

        expectedTeamDto1 = new TeamDto(1L, "Kosice", null, leagueDto, new HashSet<>(List.of(hockeyPlayerDto)));
        expectedTeamDto2 = new TeamDto(3L, "Banska Bystrica", null, leagueDto, new HashSet<>());
    }

    @Test
    void findByNameValid() {
        when(teamRepository.findByName("Kosice")).thenReturn(team1);

        TeamDto teamDto = teamService.findByName("Kosice");

        assertEquals(expectedTeamDto1, teamDto);
        verify(teamRepository, times(1)).findByName("Kosice");
    }

    @Test
    void findByNameInvalid() {
        when(teamRepository.findByName("Podbrezova")).thenReturn(null);

        TeamDto teamDto = teamService.findByName("Podbrezova");

        assertNull(teamDto);
        verify(teamRepository, times(1)).findByName("Podbrezova");
    }

    @Test
    void findAll() {
        when(teamRepository.findAll()).thenReturn(List.of(team1, team2));

        List<TeamDto> teamDtos = teamService.findAll();

        assertEquals(List.of(expectedTeamDto1, expectedTeamDto2), teamDtos);
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void findByLeagueNameValid() {
        when(teamRepository.findByLeagueName("TIPOS Extraliga")).thenReturn(List.of(team1, team2));

        List<TeamDto> teamDtos = teamService.findByLeagueName("TIPOS Extraliga");

        assertEquals(List.of(expectedTeamDto1, expectedTeamDto2), teamDtos);
        verify(teamRepository, times(1)).findByLeagueName("TIPOS Extraliga");
    }

    @Test
    void findByLeagueNameInvalid() {
        when(teamRepository.findByLeagueName("NHL")).thenReturn(List.of());

        List<TeamDto> teamDtos = teamService.findByLeagueName("NHL");

        assertEquals(List.of(), teamDtos);
        verify(teamRepository, times(1)).findByLeagueName("NHL");
    }

    @Test
    void addHockeyPlayersByIds() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        when(hockeyPlayerRepository.findAllById(any())).thenReturn(hockeyPlayers);
        when(teamRepository.save(team1)).thenReturn(team1);

        TeamDto teamDto = teamService.addHockeyPlayersByIds(1L, List.of(1L));

        assertEquals(expectedTeamDto1, teamDto);
        verify(teamRepository, times(1)).findById(1L);
        verify(hockeyPlayerRepository, times(1)).findAllById(List.of(1L));
        verify(teamRepository, times(1)).save(team1);
    }

    @Test
    void removeHockeyPlayersByIds() {
        TeamDto expectedTeamDto = new TeamDto(1L, "Kosice", null, leagueDto, new HashSet<>());
        team1.setHockeyPlayers(new HashSet<>());

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        when(hockeyPlayerRepository.findAllById(any())).thenReturn(hockeyPlayers);
        when(teamRepository.save(team1)).thenReturn(team1);

        TeamDto teamDto = teamService.removeHockeyPlayersByIds(1L, List.of(1L));

        assertEquals(expectedTeamDto, teamDto);
        verify(teamRepository, times(1)).findById(1L);
        verify(hockeyPlayerRepository, times(1)).findAllById(List.of(1L));
        verify(teamRepository, times(1)).save(team1);
    }

    @Test
    void changeLeague() {
        TeamDto expectedTeamDto = new TeamDto(1L, "Kosice", null, new LeagueDto(1L, "NHL", null), new HashSet<>(List.of()));

        team1.setLeague(league2);
        team1.setHockeyPlayers(new HashSet<>());

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        when(leagueRepository.findById(1L)).thenReturn(Optional.of(league2));
        when(teamRepository.save(team1)).thenReturn(team1);

        TeamDto teamDto = teamService.changeLeague(1L, leagueDto);

        assertEquals(expectedTeamDto, teamDto);
        verify(teamRepository, times(1)).findById(1L);
        verify(leagueRepository, times(1)).findById(1L);
    }

    @Test
    void createTeamValid() {
        when(teamRepository.save(any(Team.class))).thenReturn(team1);

        TeamDto teamDto = teamService.create(expectedTeamDto1);

        assertEquals(teamDto, expectedTeamDto1);
        verify(teamRepository, times(1)).save(any(Team.class));
    }
}

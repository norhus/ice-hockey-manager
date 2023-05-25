package cz.muni.fi.pa165.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.core.service.TeamService;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static cz.muni.fi.pa165.model.shared.enums.Scope.SCOPE_TEST_READ;
import static cz.muni.fi.pa165.model.shared.enums.Scope.SCOPE_TEST_WRITE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTests {

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TeamService teamService;

    private final LeagueDto mockLeague = new LeagueDto(1L, "TIPOS Extraliga", null);
    private final HockeyPlayerDto mockHockeyPlayer = new HockeyPlayerDto(1L, "Miroslav", "Satan",
            Instant.parse("1974-10-22T13:13:13.715Z"), "winger", 99, 99, 99,
            99, 99, 99, null);
    private final TeamDto mockTeamDto = new TeamDto(2L, "Kosice", null, mockLeague, null);
    private final TeamDto mockTeamDtoWithPlayer = new TeamDto(2L, "Kosice", null, mockLeague,
            Set.of(mockHockeyPlayer));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void getAll() throws Exception {
        when(teamService.findAll()).thenReturn(List.of(mockTeamDto));

        String response = mockMvc.perform(get("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TeamDto> teams = objectMapper.readValue(response, new TypeReference<>(){});

        assertThat(teams.size()).isNotEqualTo(0);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByNameValid() throws Exception {
        String teamName = "Kosice";

        when(teamService.findByName(teamName)).thenReturn(mockTeamDto);

        String response = mockMvc.perform(get("/api/teams/Kosice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeamDto teamDto = objectMapper.readValue(response, TeamDto.class);

        assertThat(teamDto).isEqualTo(mockTeamDto);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByNameInvalid() throws Exception {
        mockMvc.perform(get("/api/teams/Zabokreky"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByLeagueNameValid() throws Exception {
        String leagueName = "TIPOS Extraliga";

        when(teamService.findByLeagueName(leagueName)).thenReturn(List.of(mockTeamDto));

        String response = mockMvc.perform(get("/api/teams/find-by-league/TIPOS Extraliga")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TeamDto> teams = objectMapper.readValue(response, new TypeReference<>(){});

        assertThat(teams.size()).isNotEqualTo(0);
        teams.stream().map(t -> assertThat(t.league().name()).isEqualTo(leagueName));
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByLeagueNameInvalid() throws Exception {
        mockMvc.perform(get("/api/teams/find-by-league/superliga"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void addHockeyPlayersByIdsValid() throws Exception {
        when(teamService.addHockeyPlayersByIds(2L, List.of(1L))).thenReturn(mockTeamDtoWithPlayer);

        String response = mockMvc.perform(put("/api/teams/{id}/add-hockey-players", 2L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(1L))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeamDto team = objectMapper.readValue(response, TeamDto.class);

        assertThat(team).isEqualTo(mockTeamDtoWithPlayer);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void addHockeyPlayersByIdsInvalid() throws Exception {
        when(teamService.addHockeyPlayersByIds(-1L, List.of(-1L))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/api/teams/{id}/add-hockey-players", -1L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(-1L))))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void removeHockeyPlayersByIdsValid() throws Exception {
        when(teamService.removeHockeyPlayersByIds(2L, List.of(1L))).thenReturn(mockTeamDto);

        String response = mockMvc.perform(put("/api/teams/{id}/remove-hockey-players", 2L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(1L))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeamDto team = objectMapper.readValue(response, TeamDto.class);

        assertThat(team).isEqualTo(mockTeamDto);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void removeHockeyPlayersByIdsInvalid() throws Exception {
        when(teamService.removeHockeyPlayersByIds(-1L, List.of(-1L))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/api/teams/{id}/remove-hockey-players", -1L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(-1L))))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void changeLeagueValid() throws Exception {
        when(teamService.changeLeague(2L, mockLeague)).thenReturn(mockTeamDto);

        String response = mockMvc.perform(put("/api/teams/{id}/change-league", 2L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockLeague)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeamDto team = objectMapper.readValue(response, TeamDto.class);

        assertThat(team).isEqualTo(mockTeamDto);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void changeLeagueInvalid() throws Exception {
        when(teamService.changeLeague(-1L, mockLeague)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/api/teams/{id}/change-league", -1L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockLeague)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void createTeamValid() throws Exception {
        when(teamService.create(mockTeamDto)).thenReturn(mockTeamDto);

        String response = mockMvc.perform(post("/api/teams").contentType("application/json").with(csrf())
                        .content(objectMapper.writeValueAsString(mockTeamDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        TeamDto teamDto = objectMapper.readValue(response, TeamDto.class);

        assertThat(teamDto).isEqualTo(mockTeamDto);
    }
}

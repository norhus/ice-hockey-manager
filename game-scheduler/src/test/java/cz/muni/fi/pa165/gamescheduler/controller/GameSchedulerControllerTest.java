package cz.muni.fi.pa165.gamescheduler.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.gamescheduler.service.GameSchedulerDto;
import cz.muni.fi.pa165.gamescheduler.service.GameSchedulerService;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class GameSchedulerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameSchedulerService gameSchedulerService;

    @Autowired
    ObjectMapper objectMapper;

    private List<TeamDto> teams;
    private List<MatchDto> matches;

    @BeforeEach
    public void setUp() {
        LeagueDto league = new LeagueDto(1L, "NHL");

        List<LeagueDto> leagues = new ArrayList<>();
        leagues.add(league);

        teams = new ArrayList<>();
        teams.add(new TeamDto(1L, "Boston Bruins", null, leagues.get(0), List.of()));
        teams.add(new TeamDto(2L, "Tampa Bay Lightning", null, leagues.get(0), List.of()));

        matches = new ArrayList<>();
        matches.add(new MatchDto(1L, Instant.parse("2023-04-27T19:00:00.715Z"),
                null, null, teams.get(0), teams.get(1)));
    }

    @Test
    void generate() throws Exception {
        String leagueName = "NHL";
        GameSchedulerDto expected = new GameSchedulerDto(Instant.now(), teams, matches);

        when(gameSchedulerService.generate(leagueName)).thenReturn(expected);

        String response = mockMvc.perform(get("/api/game-schedulers/generate/NHL"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GameSchedulerDto actual = objectMapper.readValue(response, GameSchedulerDto.class);

        assertThat(actual.generatedAt()).isEqualTo(expected.generatedAt());
        assertThat(actual.matches().get(0)).isEqualTo(expected.matches().get(0));
        assertThat(actual.teams().get(0)).isEqualTo(expected.teams().get(0));
        assertThat(actual.teams().get(1)).isEqualTo(expected.teams().get(1));
        assertThat(actual.matches()).hasSize(1);
        assertThat(actual.teams()).hasSize(2);
    }

    @Test
    void generateAll() throws Exception {
        List<GameSchedulerDto> expected = new ArrayList<>();
        expected.add(new GameSchedulerDto(Instant.now(), teams, matches));

        when(gameSchedulerService.generateAll()).thenReturn(expected);

        String response = mockMvc.perform(get("/api/game-schedulers/generate-all"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<GameSchedulerDto> actual = objectMapper.readValue(response, new TypeReference<List<GameSchedulerDto>>() {});

        assertThat(actual).hasSize(1);
    }
}

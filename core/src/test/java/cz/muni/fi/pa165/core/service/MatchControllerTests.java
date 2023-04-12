package cz.muni.fi.pa165.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        int expLen = 3;

        String response = mockMvc.perform(get("/api/match/get-all"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<MatchDto> matches = objectMapper.readValue(response, new TypeReference<List<MatchDto>>(){});

        assertThat(matches.size()).isEqualTo(expLen);
    }

    @Test
    void findByLeagueNameValid() throws Exception {
        String expectedLeagueName = "TIPOS Extraliga";

        String response = mockMvc.perform(get("/api/match/TIPOS Extraliga"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<MatchDto> matches = objectMapper.readValue(response, new TypeReference<List<MatchDto>>(){});

        matches.stream().map(m -> assertThat(m.homeTeam().league().name()).isEqualTo(expectedLeagueName));
        matches.stream().map(m -> assertThat(m.awayTeam().league().name()).isEqualTo(expectedLeagueName));
    }

    @Test
    void findByLeagueNameInvalid() throws Exception {
        mockMvc.perform(get("/api/match/superliga"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMatchValid() throws Exception {
        LeagueDto expectedLeague = new LeagueDto(1L, "TIPOS Extraliga");
        TeamDto expectedHomeTeam = new TeamDto(2L, "Kosice", null, expectedLeague);
        TeamDto expectedAwayTeam = new TeamDto(3L, "Banska Bystrica", null, expectedLeague);
        MatchDto expectedMatch = new MatchDto(-1L, Instant.parse("2023-04-27T19:00:00.715Z"),
                null, null, expectedHomeTeam, expectedAwayTeam);

        String response = mockMvc.perform(post("/api/match/create").contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedMatch)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        MatchDto matchDto = objectMapper.readValue(response, MatchDto.class);

        assertThat(matchDto.dateOfMatch()).isEqualTo(expectedMatch.dateOfMatch());
        assertThat(matchDto.homeGoals()).isEqualTo(expectedMatch.homeGoals());
        assertThat(matchDto.awayGoals()).isEqualTo(expectedMatch.awayGoals());
        assertThat(matchDto.homeTeam()).isEqualTo(expectedMatch.homeTeam());
        assertThat(matchDto.awayTeam()).isEqualTo(expectedMatch.awayTeam());
    }

    @Test
    void updateMatchValid() throws Exception {
        LeagueDto expectedLeague = new LeagueDto(1L, "TIPOS Extraliga");
        TeamDto expectedHomeTeam = new TeamDto(3L, "Banska Bystrica", null, expectedLeague);
        TeamDto expectedAwayTeam = new TeamDto(4L, "Poprad", null, expectedLeague);
        MatchDto expectedMatch = new MatchDto(2L, Instant.parse("2023-03-26T19:00:00.715Z"),
                3, 2, expectedHomeTeam, expectedAwayTeam);

        String response = mockMvc.perform(put("/api/match/update").contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedMatch)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        MatchDto matchDto = objectMapper.readValue(response, MatchDto.class);

        assertThat(matchDto).isEqualTo(expectedMatch);
    }

    @Test
    void updateMatchInvalid() throws Exception {
        LeagueDto expectedLeague = new LeagueDto(1L, "TIPOS Extraliga");
        TeamDto expectedHomeTeam = new TeamDto(3L, "Banska Bystrica", null, expectedLeague);
        TeamDto expectedAwayTeam = new TeamDto(4L, "Poprad", null, expectedLeague);
        MatchDto expectedMatch = new MatchDto(-1L, Instant.parse("2023-03-26T19:00:00.715Z"),
                3, 2, expectedHomeTeam, expectedAwayTeam);

        mockMvc.perform(put("/api/match/update").contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedMatch)))
                .andExpect(status().isNotFound());
    }
}
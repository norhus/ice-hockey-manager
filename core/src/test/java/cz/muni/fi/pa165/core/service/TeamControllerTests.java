package cz.muni.fi.pa165.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        int expLen = 10;

        String response = mockMvc.perform(get("/api/team/get-all"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TeamDto> teams = objectMapper.readValue(response, new TypeReference<List<TeamDto>>(){});

        assertThat(teams.size()).isEqualTo(expLen);
    }

    @Test
    void findByNameValid() throws Exception {
        LeagueDto expectedLeagueDto = new LeagueDto(1L, "TIPOS Extraliga");
        TeamDto expectedTeamDto = new TeamDto(2L, "Kosice", null, expectedLeagueDto);

        String response = mockMvc.perform(get("/api/team/Kosice"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeamDto teamDto = objectMapper.readValue(response, TeamDto.class);

        assertThat(teamDto.id()).isEqualTo(expectedTeamDto.id());
        assertThat(teamDto.name()).isEqualTo(expectedTeamDto.name());
        assertThat(teamDto.league()).isEqualTo(expectedTeamDto.league());
    }

    @Test
    void findByNameInvalid() throws Exception {
        mockMvc.perform(get("/api/team/Zabokreky"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByLeagueNameValid() throws Exception {
        String expectedLeagueName = "TIPOS Extraliga";

        String response = mockMvc.perform(get("/api/team/find-by-league/TIPOS Extraliga"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TeamDto> teams = objectMapper.readValue(response, new TypeReference<List<TeamDto>>(){});

        teams.stream().map(t -> assertThat(t.league().name()).isEqualTo(expectedLeagueName));
    }

    @Test
    void findByLeagueNameInvalid() throws Exception {
        mockMvc.perform(get("/api/team/find-by-league/superliga"))
                .andExpect(status().isNotFound());
    }
}

package cz.muni.fi.pa165.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.model.dto.LeagueDto;
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
public class LeagueControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        int expLen = 2;

        String response = mockMvc.perform(get("/api/leagues"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<LeagueDto> leagues = objectMapper.readValue(response, new TypeReference<List<LeagueDto>>(){});

        assertThat(leagues.size()).isEqualTo(expLen);
    }

    @Test
    void findByNameValid() throws Exception {
        LeagueDto expectedLeagueDto = new LeagueDto(1L, "TIPOS Extraliga");

        String response = mockMvc.perform(get("/api/leagues/TIPOS Extraliga"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LeagueDto leagueDto = objectMapper.readValue(response, LeagueDto.class);

        assertThat(leagueDto.id()).isEqualTo(expectedLeagueDto.id());
        assertThat(leagueDto.name()).isEqualTo(expectedLeagueDto.name());
    }

    @Test
    void findByNameInvalid() throws Exception {
        mockMvc.perform(get("/api/leagues/superliga"))
                .andExpect(status().isNotFound());
    }
}

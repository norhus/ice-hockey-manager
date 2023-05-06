package cz.muni.fi.pa165.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.core.service.LeagueService;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cz.muni.fi.pa165.model.shared.enums.Scope.SCOPE_TEST_READ;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LeagueControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private LeagueService leagueService;

    private final LeagueDto mockLeagueDto = new LeagueDto(2L, "NHL", null);

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void getAll() throws Exception {
        when(leagueService.findAll()).thenReturn(List.of(mockLeagueDto));

        String response = mockMvc.perform(get("/api/leagues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<LeagueDto> leagues = objectMapper.readValue(response, new TypeReference<>(){});

        assertThat(leagues.size()).isNotEqualTo(0);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByNameValid() throws Exception {
        String leagueName = "NHL";
        when(leagueService.findByName(leagueName)).thenReturn(mockLeagueDto);

        String response = mockMvc.perform(get("/api/leagues/" + leagueName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LeagueDto leagueDto = objectMapper.readValue(response, LeagueDto.class);

        assertThat(leagueDto.name()).isEqualTo(leagueName);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByNameInvalid() throws Exception {
        mockMvc.perform(get("/api/leagues/superliga"))
                .andExpect(status().isNotFound());
    }
}

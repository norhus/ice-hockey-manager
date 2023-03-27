package cz.muni.fi.pa165.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
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
public class HockeyPlayerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        int expLen = 5;

        String response = mockMvc.perform(get("/api/hockey-players/get-all"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<HockeyPlayerDto> hockeyPlayers = objectMapper.readValue(response, new TypeReference<List<HockeyPlayerDto>>(){});

        assertThat(hockeyPlayers.size()).isEqualTo(expLen);
    }

    @Test
    void getAllWithoutTeam() throws Exception {
        String response = mockMvc.perform(get("/api/hockey-players/get-all-without-team"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<HockeyPlayerDto> hockeyPlayers = objectMapper.readValue(response, new TypeReference<List<HockeyPlayerDto>>(){});

        hockeyPlayers.stream().map(h -> assertThat(h.teamDto()).isEqualTo(null));
    }

    @Test
    void createHockeyPlayerValid() throws Exception {
        HockeyPlayerDto expectedHockeyPlayer = new HockeyPlayerDto(-1L, "Miroslav", "Satan",
                Instant.parse("1974-10-22T13:13:13.715Z"), "winger", 99, 99, 99,
                99, 99, 99, null);

        String response = mockMvc.perform(post("/api/hockey-players/create").contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedHockeyPlayer)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        HockeyPlayerDto hockeyPlayerDto = objectMapper.readValue(response, HockeyPlayerDto.class);

        assertThat(hockeyPlayerDto.firstName()).isEqualTo(expectedHockeyPlayer.firstName());
        assertThat(hockeyPlayerDto.lastName()).isEqualTo(expectedHockeyPlayer.lastName());
        assertThat(hockeyPlayerDto.dateOfBirth()).isEqualTo(expectedHockeyPlayer.dateOfBirth());
        assertThat(hockeyPlayerDto.position()).isEqualTo(expectedHockeyPlayer.position());
        assertThat(hockeyPlayerDto.skating()).isEqualTo(expectedHockeyPlayer.skating());
        assertThat(hockeyPlayerDto.physical()).isEqualTo(expectedHockeyPlayer.physical());
        assertThat(hockeyPlayerDto.shooting()).isEqualTo(expectedHockeyPlayer.shooting());
        assertThat(hockeyPlayerDto.defense()).isEqualTo(expectedHockeyPlayer.defense());
        assertThat(hockeyPlayerDto.puckSkills()).isEqualTo(expectedHockeyPlayer.puckSkills());
        assertThat(hockeyPlayerDto.senses()).isEqualTo(expectedHockeyPlayer.senses());
        assertThat(hockeyPlayerDto.teamDto()).isEqualTo(expectedHockeyPlayer.teamDto());
    }

    @Test
    void updateHockeyPlayerValid() throws Exception {
        HockeyPlayerDto expectedHockeyPlayer = new HockeyPlayerDto(1L, "Mae",	"Bechtelar",
                Instant.parse("1998-12-12T20:46:24.715Z"), "attacker", 90, 80,
                99, 35, 85, 40, null);

        String response = mockMvc.perform(put("/api/hockey-players/update").contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedHockeyPlayer)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HockeyPlayerDto hockeyPlayerDto = objectMapper.readValue(response, HockeyPlayerDto.class);

        assertThat(hockeyPlayerDto).isEqualTo(expectedHockeyPlayer);
    }

    @Test
    void updateHockeyPlayerInvalid() throws Exception {
        HockeyPlayerDto expectedHockeyPlayer = new HockeyPlayerDto(-1L, "Mae",	"Bechtelar",
                Instant.parse("1998-12-12T20:46:24.715Z"), "attacker", 90, 80,
                99, 35, 85, 40, null);

        mockMvc.perform(put("/api/hockey-players/update").contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedHockeyPlayer)))
                .andExpect(status().isNotFound());
    }
}

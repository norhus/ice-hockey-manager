package cz.muni.fi.pa165.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.core.service.HockeyPlayerService;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
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

import static cz.muni.fi.pa165.model.shared.enums.Scope.SCOPE_TEST_READ;
import static cz.muni.fi.pa165.model.shared.enums.Scope.SCOPE_TEST_WRITE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HockeyPlayerControllerTests {

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private HockeyPlayerService hockeyPlayerService;

    private final HockeyPlayerDto mockHockeyPlayerDto = new HockeyPlayerDto(1L, "Miroslav", "Satan",
            Instant.parse("1974-10-22T13:13:13.715Z"), "winger", 99, 99, 99,
            99, 99, 99, null);
    private final HockeyPlayerDto mockHockeyPlayerDtoUpdated = new HockeyPlayerDto(1L, "Miroslav", "Satan",
            Instant.parse("1974-10-22T13:13:13.715Z"), "center", 66, 66, 66,
            66, 66, 66, null);

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
        when(hockeyPlayerService.findAll()).thenReturn(List.of(mockHockeyPlayerDto));

        String response = mockMvc.perform(get("/api/hockey-players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<HockeyPlayerDto> hockeyPlayers = objectMapper.readValue(response, new TypeReference<>(){});

        assertThat(hockeyPlayers.size()).isNotEqualTo(0);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void getAllWithoutTeam() throws Exception {
        when(hockeyPlayerService.getAllWithoutTeam()).thenReturn(List.of(mockHockeyPlayerDto));

        String response = mockMvc.perform(get("/api/hockey-players/get-all-without-team")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<HockeyPlayerDto> hockeyPlayers = objectMapper.readValue(response, new TypeReference<>(){});

        assertThat(hockeyPlayers.size()).isNotEqualTo(0);
        hockeyPlayers.stream().map(h -> assertThat(h.teamDto()).isEqualTo(null));
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void createHockeyPlayerValid() throws Exception {
        when(hockeyPlayerService.create(mockHockeyPlayerDto)).thenReturn(mockHockeyPlayerDto);

        String response = mockMvc.perform(post("/api/hockey-players").contentType("application/json").with(csrf())
                        .content(objectMapper.writeValueAsString(mockHockeyPlayerDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        HockeyPlayerDto hockeyPlayerDto = objectMapper.readValue(response, HockeyPlayerDto.class);

        assertThat(hockeyPlayerDto).isEqualTo(mockHockeyPlayerDto);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void updateHockeyPlayerValid() throws Exception {
        when(hockeyPlayerService.update(mockHockeyPlayerDtoUpdated)).thenReturn(mockHockeyPlayerDtoUpdated);

        String response = mockMvc.perform(put("/api/hockey-players").contentType("application/json").with(csrf())
                        .content(objectMapper.writeValueAsString(mockHockeyPlayerDtoUpdated)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HockeyPlayerDto hockeyPlayerDto = objectMapper.readValue(response, HockeyPlayerDto.class);

        assertThat(hockeyPlayerDto).isEqualTo(mockHockeyPlayerDtoUpdated);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void updateHockeyPlayerInvalid() throws Exception {
        when(hockeyPlayerService.update(mockHockeyPlayerDto)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/api/hockey-players").contentType("application/json").with(csrf())
                        .content(objectMapper.writeValueAsString(mockHockeyPlayerDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByIdValid() throws Exception {
        when(hockeyPlayerService.findById(1L)).thenReturn(mockHockeyPlayerDto);

        String response = mockMvc.perform(get("/api/hockey-players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HockeyPlayerDto hockeyPlayer = objectMapper.readValue(response, HockeyPlayerDto.class);

        assertThat(hockeyPlayer).isEqualTo(mockHockeyPlayerDto);
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_READ)
    void findByIdInvalid() throws Exception {
        when(hockeyPlayerService.findById(-1L)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/api/hockey-players/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void deleteByIdValid() throws Exception {
        mockMvc.perform(delete("/api/hockey-players/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = SCOPE_TEST_WRITE)
    void deleteByIdInvalid() throws Exception {
        doThrow(new IllegalArgumentException()).when(hockeyPlayerService).deleteById(-1L);

        mockMvc.perform(delete("/api/hockey-players/-1").with(csrf()))
                .andExpect(status().isNotFound());
    }
}

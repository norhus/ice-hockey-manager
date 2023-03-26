package cz.muni.fi.pa165.leaguetable.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void findByLeague() throws Exception {
        String league = "NHL";

        String response = mockMvc.perform(get("/api/tables/" + league)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TableDto tableDto = objectMapper.readValue(response, TableDto.class);

        assertThat(tableDto.league().name()).isEqualTo(league);
    }

    @Test
    void findAll() throws Exception {
        String response = mockMvc.perform(get("/api/tables/get-all")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();;
        List<TableDto> list = objectMapper.readValue(response, new TypeReference<>(){});

        assertThat(list.size()).isNotEqualTo(0);
    }
}

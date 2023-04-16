package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.mapper.LeagueMapper;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LeagueServiceTests {

    @InjectMocks
    private LeagueService leagueService;

    @MockBean
    private LeagueRepository leagueRepository;

    @Autowired
    private LeagueMapper leagueMapper;

    League league1;
    League league2;
    LeagueDto expectedLeagueDto1 = new LeagueDto(1L, "NHL", new HashSet<>());
    LeagueDto expectedLeagueDto2 = new LeagueDto(2L, "TIPOS Extraliga", new HashSet<>());

    @BeforeEach
    void setUp() {
        leagueService = new LeagueService(leagueRepository, leagueMapper);
        league1 = new League();
        league1.setId(1L);
        league1.setName("NHL");
        league2 = new League();
        league2.setId(2L);
        league2.setName("TIPOS Extraliga");
    }

    @Test
    void findAll() {
        when(leagueRepository.findAll()).thenReturn(List.of(league1, league2));

        List<LeagueDto> leagueDtos = leagueService.findAll();

        assertEquals(leagueDtos, List.of(expectedLeagueDto1, expectedLeagueDto2));
        verify(leagueRepository, times(1)).findAll();
    }

    @Test
    void findByNameValid() {
        when(leagueRepository.findByName("NHL")).thenReturn(league1);

        LeagueDto leagueDto = leagueService.findByName("NHL");

        assertEquals(leagueDto, expectedLeagueDto1);
        verify(leagueRepository, times(1)).findByName("NHL");
    }

    @Test
    void findByNameInvalid() {
        when(leagueRepository.findByName("superliga")).thenReturn(null);

        LeagueDto leagueDto = leagueService.findByName("superliga");

        assertNull(leagueDto);
        verify(leagueRepository, times(1)).findByName("superliga");
    }
}

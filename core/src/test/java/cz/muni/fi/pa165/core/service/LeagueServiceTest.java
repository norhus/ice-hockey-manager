package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.League;
import cz.muni.fi.pa165.core.mapper.LeagueMapper;
import cz.muni.fi.pa165.core.repository.LeagueRepository;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;
    @Mock
    private LeagueMapper leagueMapper;
    @InjectMocks
    private LeagueService leagueService;

    @Test
    void findByNameNHL() {
        LeagueDto expectedLeagueDto = new LeagueDto(1L, "NHL", null);
        League expectedLeague = new League();
        expectedLeague.setId(expectedLeagueDto.id());
        expectedLeague.setName(expectedLeagueDto.name());

        when(leagueRepository.findByName(any())).thenReturn(expectedLeague);
        when(leagueMapper.toDto(any())).thenReturn(expectedLeagueDto);

        LeagueDto leagueDto = leagueService.findByName(expectedLeagueDto.name());

        assertEquals(expectedLeagueDto, leagueDto);
    }

    @Test
    void findAll() {
        List<LeagueDto> expectedDtoList = List.of(
                new LeagueDto(1L, "NHL", null),
                new LeagueDto(2L, "Slovan", null)
        );
        List<League> expectedList = new ArrayList<>();
        expectedDtoList.forEach(leagueDto -> {
            League league = new League();
            league.setId(leagueDto.id());
            league.setName(leagueDto.name());

            expectedList.add(league);
        });

        when(leagueRepository.findAll()).thenReturn(expectedList);
        when(leagueMapper.toDto(any())).thenAnswer(i -> {
            League league = i.getArgument(0);
            return new LeagueDto(league.getId(), league.getName(), null);
        });

        List<LeagueDto> leagueDtoList = leagueService.findAll();

        assertEquals(expectedDtoList, leagueDtoList);
    }
}
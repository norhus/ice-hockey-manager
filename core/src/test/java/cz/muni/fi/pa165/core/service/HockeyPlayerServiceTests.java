package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.mapper.HockeyPlayerMapper;
import cz.muni.fi.pa165.core.repository.HockeyPlayerRepository;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HockeyPlayerServiceTests {

    @InjectMocks
    private HockeyPlayerService hockeyPlayerService;

    @MockBean
    private HockeyPlayerRepository hockeyPlayerRepository;

    @Autowired
    private HockeyPlayerMapper hockeyPlayerMapper;

    HockeyPlayer hockeyPlayer1;
    HockeyPlayer hockeyPlayer2;
    HockeyPlayerDto expectedHockeyPlayerDto1 = new HockeyPlayerDto(1L, "Miroslav", "Satan",
            Instant.parse("1974-10-22T13:13:13.715Z"), "winger", 99, 99, 99,
            99, 99, 99, null);
    HockeyPlayerDto expectedHockeyPlayerDto2 = new HockeyPlayerDto(2L, "Peter", "Bondra",
            Instant.parse("1970-10-22T13:13:13.715Z"), "center", 66, 66, 66,
            66, 66, 66, null);

    @BeforeEach
    void setUp() {
        hockeyPlayerService = new HockeyPlayerService(hockeyPlayerRepository, hockeyPlayerMapper);
        hockeyPlayer1 = new HockeyPlayer();
        hockeyPlayer1.setId(1L);
        hockeyPlayer1.setFirstName("Miroslav");
        hockeyPlayer1.setLastName("Satan");
        hockeyPlayer1.setDateOfBirth(Instant.parse("1974-10-22T13:13:13.715Z"));
        hockeyPlayer1.setPosition("winger");
        hockeyPlayer1.setSkating(99);
        hockeyPlayer1.setPhysical(99);
        hockeyPlayer1.setShooting(99);
        hockeyPlayer1.setDefense(99);
        hockeyPlayer1.setPuckSkills(99);
        hockeyPlayer1.setSenses(99);
        hockeyPlayer2 = new HockeyPlayer();
        hockeyPlayer2.setId(2L);
        hockeyPlayer2.setFirstName("Peter");
        hockeyPlayer2.setLastName("Bondra");
        hockeyPlayer2.setDateOfBirth(Instant.parse("1970-10-22T13:13:13.715Z"));
        hockeyPlayer2.setPosition("center");
        hockeyPlayer2.setSkating(66);
        hockeyPlayer2.setPhysical(66);
        hockeyPlayer2.setShooting(66);
        hockeyPlayer2.setDefense(66);
        hockeyPlayer2.setPuckSkills(66);
        hockeyPlayer2.setSenses(66);
    }

    @Test
    void findAll() {
        when(hockeyPlayerRepository.findAll()).thenReturn(List.of(hockeyPlayer1, hockeyPlayer2));

        List<HockeyPlayerDto> hockeyPlayerDtos = hockeyPlayerService.findAll();

        assertEquals(hockeyPlayerDtos, List.of(expectedHockeyPlayerDto1, expectedHockeyPlayerDto2));
        verify(hockeyPlayerRepository, times(1)).findAll();
    }

    @Test
    void getAllWithoutTeam() {
        when(hockeyPlayerRepository.findAllByTeamIdIsNull()).thenReturn(List.of(hockeyPlayer1, hockeyPlayer2));

        List<HockeyPlayerDto> hockeyPlayerDtos = hockeyPlayerService.getAllWithoutTeam();

        assertEquals(hockeyPlayerDtos, List.of(expectedHockeyPlayerDto1, expectedHockeyPlayerDto2));
        verify(hockeyPlayerRepository, times(1)).findAllByTeamIdIsNull();
    }

    @Test
    void findByIdValid() {
        when(hockeyPlayerRepository.findById(hockeyPlayer1.getId())).thenReturn(Optional.of(hockeyPlayer1));

        HockeyPlayerDto hockeyPlayerDto = hockeyPlayerService.findById(hockeyPlayer1.getId());

        assertEquals(hockeyPlayerDto, expectedHockeyPlayerDto1);
        verify(hockeyPlayerRepository, times(1)).findById(hockeyPlayer1.getId());
    }

    @Test
    void findByIdInvalid() {
        when(hockeyPlayerRepository.findById(-1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> hockeyPlayerService.findById(-1L));
        verify(hockeyPlayerRepository, times(1)).findById(-1L);
    }

    @Test
    void createHockeyPlayerValid() {
        when(hockeyPlayerRepository.save(any(HockeyPlayer.class))).thenReturn(hockeyPlayer1);

        HockeyPlayerDto hockeyPlayerDto = hockeyPlayerService.create(expectedHockeyPlayerDto1);

        assertEquals(hockeyPlayerDto, expectedHockeyPlayerDto1);
        verify(hockeyPlayerRepository, times(1)).save(any(HockeyPlayer.class));
    }

    @Test
    void updateHockeyPlayerValid() {
        when(hockeyPlayerRepository.findById(hockeyPlayer1.getId())).thenReturn(Optional.of(hockeyPlayer1));
        when(hockeyPlayerRepository.save(hockeyPlayer1)).thenReturn(hockeyPlayer1);

        HockeyPlayerDto hockeyPlayerDto = hockeyPlayerService.update(expectedHockeyPlayerDto1);

        assertEquals(hockeyPlayerDto, expectedHockeyPlayerDto1);
        verify(hockeyPlayerRepository, times(1)).findById(hockeyPlayer1.getId());
        verify(hockeyPlayerRepository, times(1)).save(hockeyPlayer1);
    }

    @Test
    void updateHockeyPlayerInvalid() {
        when(hockeyPlayerRepository.findById(hockeyPlayer1.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> hockeyPlayerService.update(expectedHockeyPlayerDto1));
        verify(hockeyPlayerRepository, times(1)).findById(hockeyPlayer1.getId());
    }

    @Test
    void deleteByIdValid() {
        hockeyPlayerService.deleteById(hockeyPlayer1.getId());

        verify(hockeyPlayerRepository, times(1)).deleteById(hockeyPlayer1.getId());
    }

    @Test
    void deleteByIdInvalid() {
        doThrow(IllegalArgumentException.class).when(hockeyPlayerRepository).deleteById(-1L);

        assertThrows(IllegalArgumentException.class, () -> hockeyPlayerService.deleteById(-1L));
        verify(hockeyPlayerRepository, times(1)).deleteById(-1L);
    }
}

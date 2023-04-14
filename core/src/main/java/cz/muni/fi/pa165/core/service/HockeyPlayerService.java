package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.mapper.HockeyPlayerMapper;
import cz.muni.fi.pa165.core.repository.HockeyPlayerRepository;
import cz.muni.fi.pa165.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HockeyPlayerService {

    private final HockeyPlayerRepository hockeyPlayerRepository;
    private final HockeyPlayerMapper hockeyPlayerMapper;

    @Autowired
    public HockeyPlayerService(HockeyPlayerRepository hockeyPlayerRepository, HockeyPlayerMapper hockeyPlayerMapper) {
        this.hockeyPlayerRepository = hockeyPlayerRepository;
        this.hockeyPlayerMapper = hockeyPlayerMapper;
    }

    public HockeyPlayerDto create(HockeyPlayerDto hockeyPlayerDto) {
        return hockeyPlayerMapper.toDto(hockeyPlayerRepository.save(hockeyPlayerMapper.toEntity(hockeyPlayerDto)));
    }

    public HockeyPlayerDto update(HockeyPlayerDto hockeyPlayerDto) {
        HockeyPlayer hockeyPlayer = hockeyPlayerRepository.findById(hockeyPlayerDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + hockeyPlayerDto.id()));

        return hockeyPlayerMapper.toDto(hockeyPlayerRepository.save(hockeyPlayerMapper.update(hockeyPlayerDto, hockeyPlayer)));
    }

    public List<HockeyPlayerDto> findAll() {
        return hockeyPlayerRepository.findAll().stream()
                .map(hockeyPlayerMapper::toDto)
                .toList();
    }

    public List<HockeyPlayerDto> getAllWithoutTeam() {
        return hockeyPlayerRepository.findAllByTeamIdIsNull().stream()
                .map(hockeyPlayerMapper::toDto)
                .toList();
    }

    public HockeyPlayerDto findById(long id) {
        return hockeyPlayerMapper.toDto(
                hockeyPlayerRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("Not found hockey player with id: " + id)
                )
        );
    }

    public void deleteById(long id) {
        hockeyPlayerRepository.deleteById(id);
    }
}

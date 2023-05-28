package cz.muni.fi.pa165.core.service;

import cz.muni.fi.pa165.core.entity.HockeyPlayer;
import cz.muni.fi.pa165.core.mapper.HockeyPlayerMapper;
import cz.muni.fi.pa165.core.repository.HockeyPlayerRepository;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Hockey Player
 */
@Service
public class HockeyPlayerService {

    private final HockeyPlayerRepository hockeyPlayerRepository;
    private final HockeyPlayerMapper hockeyPlayerMapper;

    /**
     * Creates a HockeyPlayerService instance
     *
     * @param hockeyPlayerRepository HockeyPlayerRepository instance
     * @param hockeyPlayerMapper HockeyPlayerMapper instance
     */
    @Autowired
    public HockeyPlayerService(HockeyPlayerRepository hockeyPlayerRepository, HockeyPlayerMapper hockeyPlayerMapper) {
        this.hockeyPlayerRepository = hockeyPlayerRepository;
        this.hockeyPlayerMapper = hockeyPlayerMapper;
    }

    /**
     * Create Hockey Player
     *
     * @param hockeyPlayerDto HockeyPlayerDto instance
     * @return new HockeyPlayerDto instance
     */
    public HockeyPlayerDto create(HockeyPlayerDto hockeyPlayerDto) {
        return hockeyPlayerMapper.toDto(hockeyPlayerRepository.save(hockeyPlayerMapper.toEntity(hockeyPlayerDto)));
    }

    /**
     * Update Hockey Player
     *
     * @param hockeyPlayerDto HockeyPlayerDto instance
     * @return updated HockeyPlayerDto instance
     */
    public HockeyPlayerDto update(HockeyPlayerDto hockeyPlayerDto) {
        HockeyPlayer hockeyPlayer = hockeyPlayerRepository.findById(hockeyPlayerDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Not found hockey player with id: " + hockeyPlayerDto.id()));

        return hockeyPlayerMapper.toDto(hockeyPlayerRepository.save(hockeyPlayerMapper.update(hockeyPlayerDto, hockeyPlayer)));
    }

    /**
     * Get all Hockey Players
     *
     * @return all HockeyPlayerDto instances
     */
    public List<HockeyPlayerDto> findAll() {
        return hockeyPlayerRepository.findAll().stream()
                .map(hockeyPlayerMapper::toDto)
                .toList();
    }

    /**
     * Get all Hockey Players without Team
     *
     * @return all HockeyPlayerDto instances without Team
     */
    public List<HockeyPlayerDto> getAllWithoutTeam() {
        return hockeyPlayerRepository.findAllByTeamIdIsNull().stream()
                .map(hockeyPlayerMapper::toDto)
                .toList();
    }

    /**
     * Get Hockey Player by id
     *
     * @return HockeyPlayerDto instance by id
     */
    public HockeyPlayerDto findById(long id) {
        return hockeyPlayerMapper.toDto(
                hockeyPlayerRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("Not found hockey player with id: " + id)
                )
        );
    }

    /**
     * Delete Hockey Player by id
     */
    public void deleteById(long id) {
        hockeyPlayerRepository.deleteById(id);
    }
}

package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.core.service.LeagueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<LeagueDto>> getAll() {
        return ResponseEntity.ok(leagueService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<LeagueDto> findByName(@PathVariable String name) {
        LeagueDto leagueDto = leagueService.findByName(name);
        if (leagueDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leagueDto);
    }

    @PostMapping
    public ResponseEntity<LeagueDto> create(@Valid @RequestBody LeagueDto leagueDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leagueService.create(leagueDto));
    }
}

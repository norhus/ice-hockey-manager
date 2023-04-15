package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.core.service.MatchService;
import cz.muni.fi.pa165.model.dto.MatchDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchDto>> getAll() {
        return ResponseEntity.ok(matchService.findAll());
    }

    @PostMapping
    public ResponseEntity<MatchDto> create(@Valid @RequestBody MatchDto matchDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matchService.create(matchDto));
    }

    @PutMapping
    public ResponseEntity<MatchDto> update(@Valid @RequestBody MatchDto matchDto) {
        try {
            return ResponseEntity.ok(matchService.update(matchDto));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{league}")
    public ResponseEntity<List<MatchDto>> findByLeagueName(@PathVariable String league) {
        List<MatchDto> matches = matchService.findByLeagueName(league);
        if (matches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/find-unplayed-matches")
    public ResponseEntity<List<MatchDto>> findUnplayedMatchesBeforeNow() {
        List<MatchDto> matches = matchService.findUnplayedMatchesBeforeNow();
        if (matches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/play-unplayed-matches")
    public ResponseEntity<List<MatchDto>> playUnplayedMatches() {
        List<MatchDto> matches = matchService.playUnplayedMatches();
        if (matches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matches);
    }
}

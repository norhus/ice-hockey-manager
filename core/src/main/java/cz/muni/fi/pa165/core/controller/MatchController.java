package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.core.service.MatchService;
import cz.muni.fi.pa165.model.dto.MatchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Match Controller", description = "Controller for manipulation with Match Entity")
@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @Operation(
            summary = "Get all matches",
            description = "Retrieves all matches"
    )
    @GetMapping
    public ResponseEntity<List<MatchDto>> getAll() {
        return ResponseEntity.ok(matchService.findAll());
    }

    @Operation(
            summary = "Create a match",
            description = "Creates a new match"
    )
    @PostMapping
    public ResponseEntity<MatchDto> create(@Valid @RequestBody MatchDto matchDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matchService.create(matchDto));
    }

    @Operation(
            summary = "Update a match",
            description = "Updates an existing match"
    )
    @PutMapping
    public ResponseEntity<MatchDto> update(@Valid @RequestBody MatchDto matchDto) {
        try {
            return ResponseEntity.ok(matchService.update(matchDto));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get matches by league",
            description = "Retrieves matches by the name of the league"
    )
    @GetMapping("/{league}")
    public ResponseEntity<List<MatchDto>> findByLeagueName(@PathVariable String league) {
        return ResponseEntity.ok(matchService.findByLeagueName(league));
    }

    @Operation(
            summary = "Get unplayed matches",
            description = "Retrieves unplayed matches before the current time"
    )
    @GetMapping("/find-unplayed-matches")
    public ResponseEntity<List<MatchDto>> findUnplayedMatchesBeforeNow() {
        return ResponseEntity.ok(matchService.findUnplayedMatchesBeforeNow());
    }

    @Operation(
            summary = "Play unplayed matches",
            description = "Plays the unplayed matches and updates their scores randomly"
    )
    @GetMapping("/play-unplayed-matches")
    public ResponseEntity<List<MatchDto>> playUnplayedMatches() {
        return ResponseEntity.ok(matchService.playUnplayedMatches());
    }

    @Operation(
            summary = "Get played matches by league",
            description = "Retrieves played matches in a specific league"
    )
    @GetMapping("/find-played-matches/{league}")
    public ResponseEntity<List<MatchDto>> findPlayedMatchesByLeague(@PathVariable String league) {
        return ResponseEntity.ok(matchService.findPlayedMatchesByLeague(league));
    }

}

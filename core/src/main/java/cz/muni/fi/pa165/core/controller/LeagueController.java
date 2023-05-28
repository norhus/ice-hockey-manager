package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.core.service.LeagueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "League Controller", description = "Controller for manipulation with League Entity")
@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @Operation(
            summary = "Get all leagues",
            description = "Returns leagues"
    )
    @GetMapping
    public ResponseEntity<List<LeagueDto>> getAll() {
        return ResponseEntity.ok(leagueService.findAll());
    }

    @Operation(
            summary = "Get league by name",
            description = "Returns league by name"
    )
    @GetMapping("/{name}")
    public ResponseEntity<LeagueDto> findByName(@PathVariable String name) {
        LeagueDto leagueDto = leagueService.findByName(name);
        if (leagueDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leagueDto);
    }

    @Operation(
            summary = "Create league (only Admin)",
            description = "Returns new league"
    )
    @PostMapping
    public ResponseEntity<LeagueDto> create(@Valid @RequestBody LeagueDto leagueDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leagueService.create(leagueDto));
    }
}

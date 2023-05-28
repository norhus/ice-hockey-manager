package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.core.service.TeamService;
import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.TeamDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Team Controller", description = "Controller for manipulation with Team Entity")
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(
            summary = "Get Team by name",
            description = "Returns Team by name"
    )
    @GetMapping("/{name}")
    public ResponseEntity<TeamDto> findByName(@PathVariable String name) {
        TeamDto teamDto = teamService.findByName(name);
        if (teamDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teamDto);
    }

    @Operation(
            summary = "Get all Teams",
            description = "Returns Teams"
    )
    @GetMapping
    public ResponseEntity<List<TeamDto>> getAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @Operation(
            summary = "Get all Teams by League name",
            description = "Returns Teams by League name"
    )
    @GetMapping("/find-by-league/{league}")
    public ResponseEntity<List<TeamDto>> findByLeagueName(@PathVariable String league) {
        List<TeamDto> teams = teamService.findByLeagueName(league);
        if (teams.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teams);
    }

    @Operation(
            summary = "Add Hockey Players to Team by id",
            description = "Returns team with new hockey players"
    )
    @PutMapping("/{id}/add-hockey-players")
    public ResponseEntity<TeamDto> addHockeyPlayersByIds(@PathVariable long id, @RequestBody List<Long> hockeyPlayerIds) {
        try {
            return ResponseEntity.ok(teamService.addHockeyPlayersByIds(id, hockeyPlayerIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Remove Hockey Players from Team by id",
            description = "Returns team with new hockey players"
    )
    @PutMapping("/{id}/remove-hockey-players")
    public ResponseEntity<TeamDto> removeHockeyPlayersByIds(@PathVariable long id, @RequestBody List<Long> hockeyPlayerIds) {
        try {
            return ResponseEntity.ok(teamService.removeHockeyPlayersByIds(id, hockeyPlayerIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Change league in Team by id",
            description = "Returns team with new league"
    )
    @PutMapping("/{id}/change-league")
    public ResponseEntity<TeamDto> changeLeague(@PathVariable long id, @RequestBody LeagueDto leagueDto) {
        try {
            return ResponseEntity.ok(teamService.changeLeague(id, leagueDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create Team (only Admin)",
            description = "Returns new Team"
    )
    @PostMapping
    public ResponseEntity<TeamDto> create(@Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teamService.create(teamDto));
    }
}

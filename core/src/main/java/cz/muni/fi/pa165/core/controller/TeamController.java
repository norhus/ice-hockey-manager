package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.TeamDto;
import cz.muni.fi.pa165.core.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<TeamDto> findByName(@PathVariable String name) {
        TeamDto teamDto = teamService.findByName(name);
        if (teamDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teamDto);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping("/find-by-league/{league}")
    public ResponseEntity<List<TeamDto>> findByLeagueName(@PathVariable String league) {
        List<TeamDto> teams = teamService.findByLeagueName(league);
        if (teams.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teams);
    }
}

package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.TeamDto;
import cz.muni.fi.pa165.core.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<TeamDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(teamService.findByName(name));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TeamDto>> getAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping("/find-by-league/{league}")
    public ResponseEntity<List<TeamDto>> findByLeagueName(@PathVariable String league) {
        return ResponseEntity.ok(teamService.findByLeagueName(league));
    }
}

package cz.muni.fi.pa165.icehockeymanager.controller;

import cz.muni.fi.pa165.icehockeymanager.dto.TeamDto;
import cz.muni.fi.pa165.icehockeymanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

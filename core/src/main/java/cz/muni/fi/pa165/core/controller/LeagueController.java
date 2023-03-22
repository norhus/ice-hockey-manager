package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.core.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/league")
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<LeagueDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(leagueService.findByName(name));
    }
}

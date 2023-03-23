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
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<MatchDto>> getAll() {
        return ResponseEntity.ok(matchService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<MatchDto> create(@Valid @RequestBody MatchDto matchDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matchService.create(matchDto));
    }
}

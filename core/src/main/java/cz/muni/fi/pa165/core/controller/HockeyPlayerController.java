package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.core.service.HockeyPlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hockey-players")
public class HockeyPlayerController {

    private final HockeyPlayerService hockeyPlayerService;

    @Autowired
    public HockeyPlayerController(HockeyPlayerService hockeyPlayerService) {
        this.hockeyPlayerService = hockeyPlayerService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<HockeyPlayerDto>> getAll() {
        return ResponseEntity.ok(hockeyPlayerService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<HockeyPlayerDto> create(@Valid @RequestBody HockeyPlayerDto hockeyPlayerDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hockeyPlayerService.create(hockeyPlayerDto));
    }

    @PutMapping("/update")
    public ResponseEntity<HockeyPlayerDto> update(@Valid @RequestBody HockeyPlayerDto hockeyPlayerDto) {
        return ResponseEntity.ok(hockeyPlayerService.update(hockeyPlayerDto));
    }
}

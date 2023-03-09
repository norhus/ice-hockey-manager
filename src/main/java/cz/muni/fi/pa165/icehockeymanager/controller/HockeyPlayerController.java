package cz.muni.fi.pa165.icehockeymanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hockey-players")
public class HockeyPlayerController {

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        System.out.println("adssdad");

        return ResponseEntity.ok(true);
    }
}

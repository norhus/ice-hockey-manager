package cz.muni.fi.pa165.gamescheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
class GameSchedulerController {

    private final GameSchedulerService gameSchedulerService;

    @Autowired
    public GameSchedulerController(GameSchedulerService gameSchedulerService) {
        this.gameSchedulerService = gameSchedulerService;
    }

    @GetMapping("/generate")
    public GameSchedulerDto generate(@RequestParam String leagueName) {
        return gameSchedulerService.generate(leagueName);
    }

}

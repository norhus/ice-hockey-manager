package cz.muni.fi.pa165.gamescheduler.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@OpenAPIDefinition(
        info = @Info(
                title = "Game Scheduler Service",
                version = "1.0",
                contact = @Contact(
                        name = "Norbert Husarčík",
                        url = "https://is.muni.cz/auth/osoba/485530",
                        email = "485530@mail.muni.cz"
                ),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        ),
        servers = @Server(
                description = "Server",
                url = "{scheme}://{server}:{port}",
                variables = {
                        @ServerVariable(name = "scheme", allowableValues = {"http", "https"}, defaultValue = "http"),
                        @ServerVariable(name = "server", defaultValue = "localhost"),
                        @ServerVariable(name = "port", defaultValue = "8082")
                })
)
@Tag(name = "Game Scheduler", description = "Microservice for Game Scheduler")
@RequestMapping("/api/game-scheduler")
class GameSchedulerController {

    private final GameSchedulerService gameSchedulerService;

    @Autowired
    public GameSchedulerController(GameSchedulerService gameSchedulerService) {
        this.gameSchedulerService = gameSchedulerService;
    }

    @Operation(
            summary = "Generate league matches by league name",
            description = "Returns game scheduler by league name"
    )
    @GetMapping("/generate/{leagueName}")
    public GameSchedulerDto generate(@PathVariable String leagueName) {
        return gameSchedulerService.generate(leagueName);
    }

    @Operation(
            summary = "Generate league matches for each league",
            description = "Return an array of objects representing game schedulers"
    )
    @GetMapping("/generate-all")
    public List<GameSchedulerDto> generateAll() {
        return gameSchedulerService.generateAll();
    }
}

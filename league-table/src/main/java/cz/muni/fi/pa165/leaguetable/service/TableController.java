package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.leaguetable.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@OpenAPIDefinition(
        info = @Info(
                title = "League Table Service",
                version = "1.0",
                contact = @Contact(
                        name = "Ján Homola",
                        url = "https://is.muni.cz/auth/osoba/540464",
                        email = "540464@mail.muni.cz"
                ),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        ),
        servers = @Server(
                description = "Server",
                url = "{scheme}://{server}:{port}",
                variables = {
                        @ServerVariable(name = "scheme", allowableValues = {"http", "https"}, defaultValue = "http"),
                        @ServerVariable(name = "server", defaultValue = "localhost"),
                        @ServerVariable(name = "port", defaultValue = "8083")
                })
)
@Tag(name = "League Table", description = "Microservice for League Table")
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @Operation(
            summary = "Get league table by league name",
            description = "Returns league table by league name"
    )
    @GetMapping("/{league}")
    public ResponseEntity<TableDto> findByLeague(@PathVariable String league) throws ResourceNotFoundException {
        return ResponseEntity.ok(tableService.findByLeague(league));
    }

    @Operation(
            summary = "Get all league tables",
            description = "Return an array of  object representing league tables"
    )
    @GetMapping("/get-all")
    public ResponseEntity<List<TableDto>> getAll() throws ResourceNotFoundException {
            return ResponseEntity.ok(tableService.findAll());
    }
}

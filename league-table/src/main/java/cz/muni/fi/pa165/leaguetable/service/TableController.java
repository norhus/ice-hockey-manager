package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.shared.utils.Utils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Spring REST Controller for league table
 */
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

    /**
     * Retrieves the league table for a specific league by its name.
     *
     * @param league  the name of the league for which to retrieve the table.
     * @param request the HttpServletRequest for accessing the request information.
     * @return a ResponseEntity containing the league table if found, or an appropriate error response.
     */
    @Operation(
            summary = "Get league table by league name",
            description = "Returns league table by league name"
    )
    @GetMapping("/{league}")
    public ResponseEntity<TableDto> findByLeague(@PathVariable String league, HttpServletRequest request) {

        return ResponseEntity.ok(tableService.findByLeague(league, Utils.getToken(request)));
    }

    /**
     * Retrieves all league tables.
     *
     * @param request the HttpServletRequest for accessing the request information.
     * @return a ResponseEntity containing a list of league tables, or an appropriate error response.
     */
    @Operation(
            summary = "Get all league tables",
            description = "Return an array of  object representing league tables"
    )
    @GetMapping
    public ResponseEntity<List<TableDto>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(tableService.findAll(Utils.getToken(request)));
    }
}

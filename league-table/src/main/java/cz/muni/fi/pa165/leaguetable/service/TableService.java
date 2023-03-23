package cz.muni.fi.pa165.leaguetable.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TableService {

    public final WebClient client = WebClient.create("http://localhost:8080/");
    public TableDto findByLeague(String leagueName) {
        return new TableDto();
    }
}

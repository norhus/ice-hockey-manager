package cz.muni.fi.pa165.leaguetable.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TableServiceTest {

    @Autowired
    TableService tableService;

    @Test
    void findByLeague() {
        String league = "NHL"; // initialization

        TableDto tableDto = tableService.findByLeague(league); // execution

        assertThat(tableDto.league().name()).isEqualTo(league); // assertion
    }

    @Test
    void findAll() {
        List<TableDto> tables = tableService.findAll();

        assertThat(tables.size()).isNotEqualTo(0);
    }
}

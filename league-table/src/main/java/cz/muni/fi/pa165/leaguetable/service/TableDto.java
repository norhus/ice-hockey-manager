package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;

import java.io.Serializable;
import java.util.List;


public record TableDto (

    LeagueDto league,

    List<TableRowDto> teams

) implements Serializable {
}

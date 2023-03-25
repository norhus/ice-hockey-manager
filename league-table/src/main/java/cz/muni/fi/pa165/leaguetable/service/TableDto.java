package cz.muni.fi.pa165.leaguetable.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;

public class TableDto {

    private LeagueDto league;

    public void setLeague(LeagueDto league) {
        this.league = league;
    }

    public LeagueDto getLeague() {
        return league;
    }
}

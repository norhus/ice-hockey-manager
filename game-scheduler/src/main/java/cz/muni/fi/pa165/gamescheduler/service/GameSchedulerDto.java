package cz.muni.fi.pa165.gamescheduler.service;

import cz.muni.fi.pa165.model.dto.LeagueDto;
import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;

import java.time.Instant;
import java.util.List;

public class GameSchedulerDto {

    private Instant generatedAt;

    private List<TeamDto> teams;

    private List<MatchDto> matches;

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public List<MatchDto> getMatches() {
        return matches;
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setMatches(List<MatchDto> matches) {
        this.matches = matches;
    }

    public void setTeams(List<TeamDto> teams) {
        this.teams = teams;
    }
}

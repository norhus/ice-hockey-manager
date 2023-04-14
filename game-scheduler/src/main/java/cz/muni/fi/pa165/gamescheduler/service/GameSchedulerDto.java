package cz.muni.fi.pa165.gamescheduler.service;

import cz.muni.fi.pa165.model.dto.MatchDto;
import cz.muni.fi.pa165.model.dto.TeamDto;

import java.time.Instant;
import java.util.List;

public record GameSchedulerDto (
        Instant generatedAt,
        List<TeamDto> teams,
        List<MatchDto> matches
) {
}

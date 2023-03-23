package cz.muni.fi.pa165.model.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the Match entity
 */
public record MatchDto(
        Long id,
        Instant dateOfMatch,
        Integer homeGoals,
        Integer awayGoals,
        TeamDto homeTeam,
        TeamDto awayTeam
) implements Serializable {
}
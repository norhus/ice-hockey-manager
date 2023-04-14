package cz.muni.fi.pa165.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * A DTO for the Match entity
 */
public record MatchDto(

        @Schema(example = "1")
        Long id,

        @Schema(example = "2023-04-27 00:00:00-07")
        Instant dateOfMatch,

        @Schema(example = "0")
        Integer homeGoals,

        @Schema(example = "0")
        Integer awayGoals,
        TeamDto homeTeam,
        TeamDto awayTeam
) {
}

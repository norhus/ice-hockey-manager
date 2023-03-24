package cz.muni.fi.pa165.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * A DTO for the HockeyPlayer entity
 */
public record HockeyPlayerDto(

        @Schema(example= "1")
        Long id,

        @Schema(example= "Steve")
        @Size(max = 64)
        String firstName,

        @Schema(example= "Jons")
        @Size(max = 64)
        String lastName,

        @Schema(example= "2023-03-10T19:46:24.155688Z")
        Instant dateOfBirth,

        @Schema(example= "attacker")
        @Size(max = 16)
        String position,

        @Schema(example= "968")
        Integer skating,

        @Schema(example= "753")
        Integer physical,

        @Schema(example= "992")
        Integer shooting,

        @Schema(example= "246")
        Integer defense,

        @Schema(example= "953")
        Integer puckSkills,

        @Schema(example= "298")
        Integer senses,

        TeamDto teamDto
) {
}

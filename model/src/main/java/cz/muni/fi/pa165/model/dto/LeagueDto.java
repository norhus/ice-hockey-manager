package cz.muni.fi.pa165.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO for the League entity
 */
public record LeagueDto(
        @Schema(example = "1")
        Long id,

        @Schema(example = "TIPOS Extraliga")
        @Size(max = 64)
        @NotNull
        String name
) {
}

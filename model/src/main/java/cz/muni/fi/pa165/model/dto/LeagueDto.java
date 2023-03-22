package cz.muni.fi.pa165.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * A DTO for the League entity
 */
public record LeagueDto(
        Long id,

        @Size(max = 64)
        @NotNull
        String name
) implements Serializable {
}

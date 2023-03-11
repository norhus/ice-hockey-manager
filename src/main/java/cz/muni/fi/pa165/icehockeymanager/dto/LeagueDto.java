package cz.muni.fi.pa165.icehockeymanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * A DTO for the {@link cz.muni.fi.pa165.icehockeymanager.entity.League} entity
 */
public record LeagueDto(
        Long id,

        @Size(max = 64)
        @NotNull
        String name
) implements Serializable {
}

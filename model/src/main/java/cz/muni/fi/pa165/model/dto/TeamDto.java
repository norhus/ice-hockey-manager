package cz.muni.fi.pa165.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * A DTO for the Team entity
 */
public record TeamDto (
        Long id,
        @Size(max = 64)
        @NotNull
        String name,
        UserDto appUser,
        LeagueDto league
) implements Serializable {
}
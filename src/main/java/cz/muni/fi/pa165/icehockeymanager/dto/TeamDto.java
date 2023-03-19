package cz.muni.fi.pa165.icehockeymanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * A DTO for the {@link cz.muni.fi.pa165.icehockeymanager.entity.Team} entity
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
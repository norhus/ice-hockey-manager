package cz.muni.fi.pa165.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the Team entity
 */
public record TeamDto (

        @Schema(example= "1")
        Long id,

        @Schema(example = "Poprad")
        @Size(max = 64)
        @NotNull
        String name,
        UserDto appUser,
        LeagueDto league,

        List<HockeyPlayerDto> hockeyPlayers
) {
}

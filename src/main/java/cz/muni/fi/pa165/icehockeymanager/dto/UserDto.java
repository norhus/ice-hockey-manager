package cz.muni.fi.pa165.icehockeymanager.dto;

import cz.muni.fi.pa165.icehockeymanager.shared.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO for the {@link cz.muni.fi.pa165.icehockeymanager.entity.User} entity
 */
public record UserDto(
        @Size(max = 1024)
        @NotNull
        @Schema(example= "user@gmail.com")
        String email,

        @Size(max = 64)
        @NotNull
        @Schema(example= "Password1")
        String password,

        @Schema(hidden = true)
        Role role
) {
}

package cz.muni.fi.pa165.icehockeymanager.dto;

import cz.muni.fi.pa165.icehockeymanager.shared.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO for the {@link cz.muni.fi.pa165.icehockeymanager.entity.User} entity
 */
public record UserDto(

        @Schema(example= "user@gmail.com")
        @Size(max = 1024)
        @NotNull
        String email,

        @Schema(example= "Password1")
        @Size(max = 64)
        @NotNull
        String password,

        @Schema(hidden = true)
        Role role
) {
}

package cz.muni.fi.pa165.model.dto;

import cz.muni.fi.pa165.model.shared.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO for the User entity
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

package cz.muni.fi.pa165.icehockeymanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * A DTO for the {@link cz.muni.fi.pa165.icehockeymanager.entity.HockeyPlayer} entity
 */
public record HockeyPlayerDto(

        Long id,

        @Size(max = 64)
        String firstName,

        @Size(max = 64)
        String lastName,

        Instant dateOfBirth,

        @Size(max = 16)
        String position,

        Integer skating,

        Integer physical,

        Integer shooting,

        Integer defense,

        Integer puckSkills,

        Integer senses
) {
}

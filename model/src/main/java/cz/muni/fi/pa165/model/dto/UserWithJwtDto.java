package cz.muni.fi.pa165.model.dto;

import cz.muni.fi.pa165.model.shared.enums.Role;

public record UserWithJwtDto (
        String email,
        Role role,
        String jwtToken
) {
}

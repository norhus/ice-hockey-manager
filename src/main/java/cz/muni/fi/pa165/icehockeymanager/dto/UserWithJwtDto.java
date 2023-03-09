package cz.muni.fi.pa165.icehockeymanager.dto;

import cz.muni.fi.pa165.icehockeymanager.shared.enums.Role;

public record UserWithJwtDto (
        String email,
        Role role,
        String jwtToken
) {
}

package cz.muni.fi.pa165.icehockeymanager.dto;

import cz.muni.fi.pa165.icehockeymanager.validation.annotation.FieldsValueMatch;
import cz.muni.fi.pa165.icehockeymanager.validation.annotation.ValidPassword;

import javax.validation.constraints.Email;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public record UserRegisterDto(

        @Email(message = "Email is not valid", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        String email,

        @ValidPassword
        String password,

        String confirmPassword
) {
}

package cz.muni.fi.pa165.icehockeymanager.dto;

import cz.muni.fi.pa165.icehockeymanager.validation.annotation.FieldsValueMatch;
import cz.muni.fi.pa165.icehockeymanager.validation.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public record UserRegisterDto(

        @Schema(example= "adambarca333@gmail.com")
        @Size(max = 1024)
        @Email(message = "Email is not valid", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        String email,

        @Schema(example= "Password1")
        @ValidPassword
        String password,

        @Schema(example= "Password1")
        String confirmPassword
) {
}

package cz.muni.fi.pa165.model.validation.validator;

import cz.muni.fi.pa165.model.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import java.util.Arrays;
import java.util.StringJoiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 64),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1)));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        validator.getMessages(result).forEach(joiner::add);

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(joiner.toString())
                .addConstraintViolation();
        return false;
    }
}

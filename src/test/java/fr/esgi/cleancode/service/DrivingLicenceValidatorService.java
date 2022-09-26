package fr.esgi.cleancode.service;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.validate;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceValidatorService {

    private final DrivingLicenceValidatorService service = new DrivingLicenceValidatorService();

    @Test
    void should_validated() {
        String securitySocialNumber = "123456789123456";
        val license =  DrivingLicence.builder().driverSocialSecurityNumber(securitySocialNumber).build();
        assertThat(service.isValidSSNumber(license)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_not_validated(String securitySocialNumber) {
        val license =  DrivingLicence.builder().driverSocialSecurityNumber(securitySocialNumber).build();
        val isValidated = service.isValidSSNumber(license);
        assertThat(isValidated).containsInvalidInstanceOf(InvalidDriverSocialSecurityNumberException.class);
    }

}

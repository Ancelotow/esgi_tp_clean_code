package fr.esgi.cleancode.service;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceValidatorServiceTest {

    private final DrivingLicenceValidatorService service = new DrivingLicenceValidatorService();

    @Test
    void should_validated() {
        String securitySocialNumber = "123456789123456";
        val drivingLicence =  DrivingLicence.builder().driverSocialSecurityNumber(securitySocialNumber).build();
        assertThat(service.isValidSSNumber(drivingLicence)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"12345678A123456", "123344", "12376876876876878678678344", "A"})
    void should_not_validated(String securitySocialNumber) {
        val drivingLicence =  DrivingLicence.builder().driverSocialSecurityNumber(securitySocialNumber).build();
        assertThatExceptionOfType(InvalidDriverSocialSecurityNumberException.class).isThrownBy(() -> service.isValidSSNumber(drivingLicence));
    }

}

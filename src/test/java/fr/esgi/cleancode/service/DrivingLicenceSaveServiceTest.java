package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceSaveServiceTest {

    @InjectMocks
    private DrivingLicenceSaveService service;

    @Mock
    private InMemoryDatabase database;

    @Mock
    private DrivingLicenceValidatorService serviceValidator;

    @Test
    void should_check_return() {
        String securitySocialNumber = "123456789123456";
        val id = new DrivingLicenceIdGenerationService().generateNewDrivingLicenceId();
        final var drivingLicense = DrivingLicence.builder().driverSocialSecurityNumber(securitySocialNumber).id(id).build();

        when(database.save(id, drivingLicense)).thenReturn(drivingLicense);

        final var actual = service.save(drivingLicense);

        assertThat(actual).isEqualTo(drivingLicense);
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }

    @Test
    void should_check_twelve_point() {
        String securitySocialNumber = "123456789123456";
        val id = new DrivingLicenceIdGenerationService().generateNewDrivingLicenceId();
        final var drivingLicense = DrivingLicence.builder().driverSocialSecurityNumber(securitySocialNumber).id(id).build();

        when(database.save(id, drivingLicense)).thenReturn(drivingLicense);

        final var actual = service.save(drivingLicense);

        assertThat(actual.getAvailablePoints()).isEqualTo(12);
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }

}

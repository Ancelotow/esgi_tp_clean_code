package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceCreatorServiceTest {

    @InjectMocks
    private DrivingLicenceCreatorService service;

    @Mock
    private InMemoryDatabase database;

    @Mock
    private DrivingLicenceIdGenerationService serviceIdGenerator;

    @Mock
    private DrivingLicenceValidatorService serviceValidator;

    @Test
    void should_check_return() {
        final var id = UUID.randomUUID();
        String securitySocialNumber = "123456789123456";
        final var drivingLicense = DrivingLicence.builder().id(id).driverSocialSecurityNumber(securitySocialNumber).build();

        when(serviceIdGenerator.generateNewDrivingLicenceId()).thenReturn(id);
        when(serviceValidator.isValidSSNumber(drivingLicense)).thenReturn(true);
        when(database.save(id, drivingLicense)).thenReturn(drivingLicense);

        final var actual = service.save(drivingLicense);

        assertThat(actual).isEqualTo(drivingLicense);
        verify(database).save(id, drivingLicense);
        verifyNoMoreInteractions(database);
        verifyNoMoreInteractions(serviceValidator);
    }

    @Test
    void should_check_twelve_point() {
        final var id = UUID.randomUUID();
        String securitySocialNumber = "123456789123456";
        final var drivingLicense = DrivingLicence.builder().id(id).driverSocialSecurityNumber(securitySocialNumber).build();

        when(serviceIdGenerator.generateNewDrivingLicenceId()).thenReturn(id);
        when(serviceValidator.isValidSSNumber(drivingLicense)).thenReturn(true);
        when(database.save(id, drivingLicense)).thenReturn(drivingLicense);

        final var actual = service.save(drivingLicense);

        assertThat(actual.getAvailablePoints()).isEqualTo(12);
        verify(database).save(id, drivingLicense);
        verifyNoMoreInteractions(database);
        verifyNoMoreInteractions(serviceValidator);
    }

}

package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DrivingLicenceRemoverPointsServiceTest {

    @InjectMocks
    private final DrivingLicenceRemoverPointsService service;

    @Mock
    private DrivingLicenceFinderService serviceFinder;

    @Mock
    private InMemoryDatabase database;

    @ParameterizedTest
    @ValueSource(ints = {2, 12, 19})
    void should_remove_points(int points) {
        final var id = UUID.randomUUID();
        String securitySocialNumber = "123456789123456";
        final var drivingLicense = DrivingLicence.builder().id(id).driverSocialSecurityNumber(securitySocialNumber).build();

        when(serviceFinder.findById(id)).thenReturn(Optional.ofNullable(drivingLicense));
        when(database.save(id, drivingLicense)).thenReturn(drivingLicense);

        final var actual = service.removePoints(points, id);

        assertThat(actual.getAvailablePoints()).isGreaterThanOrEqualTo(0);
        verifyNoMoreInteractions(serviceFinder);
        verifyNoMoreInteractions(database);

    }

    @Test
    void should_not_remove_points() {
        final var id = UUID.randomUUID();

        when(serviceFinder.findById(id)).thenReturn(null);

        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> service.removePoints(3, id));
        verifyNoMoreInteractions(serviceFinder);
    }

}

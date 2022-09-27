package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import net.bytebuddy.dynamic.DynamicType;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemoverPointsServiceTest {

    @InjectMocks
    private DrivingLicenceRemoverPointsService service;

    @Mock
    private DrivingLicenceFinderService serviceFinder;

    @Mock
    private InMemoryDatabase database;

    @Captor
    private ArgumentCaptor<DrivingLicence> entityCaptor;


    @Test
    void should_remove_points() {
        final var id = UUID.randomUUID();
        String securitySocialNumber = "123456789123456";
        final var pointsToRemove = 4;
        final var drivingLicense = DrivingLicence.builder().id(id).driverSocialSecurityNumber(securitySocialNumber).build();

        when(serviceFinder.findById(id)).thenReturn(Optional.ofNullable(drivingLicense));
        when(database.save(eq(id), any(DrivingLicence.class))).thenReturn(drivingLicense);

        final var actual = service.removePointsById(pointsToRemove, id);

        verify(database).save(eq(id), entityCaptor.capture());
        verifyNoMoreInteractions(serviceFinder);
        verifyNoMoreInteractions(database);

        assertThat(actual).usingRecursiveComparison().isEqualTo(drivingLicense);
        assertThat(entityCaptor.getValue().getAvailablePoints()).usingRecursiveComparison().isEqualTo(8);
    }

    @Test
    void should_remove_all_points() {
        final var id = UUID.randomUUID();
        String securitySocialNumber = "123456789123456";
        final var pointsToRemove = 100;
        final var drivingLicense = DrivingLicence.builder().id(id).driverSocialSecurityNumber(securitySocialNumber).build();

        when(serviceFinder.findById(id)).thenReturn(Optional.ofNullable(drivingLicense));
        when(database.save(eq(id), any(DrivingLicence.class))).thenReturn(drivingLicense);

        final var actual = service.removePointsById(pointsToRemove, id);

        verify(database).save(eq(id), entityCaptor.capture());
        verifyNoMoreInteractions(serviceFinder);
        verifyNoMoreInteractions(database);

        assertThat(actual).usingRecursiveComparison().isEqualTo(drivingLicense);
        assertThat(entityCaptor.getValue().getAvailablePoints()).usingRecursiveComparison().isEqualTo(0);
    }

    @Test
    void should_not_remove_points() {
        final var id = UUID.randomUUID();

        when(serviceFinder.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> service.removePointsById(3, id));
        verifyNoMoreInteractions(serviceFinder);
    }

}

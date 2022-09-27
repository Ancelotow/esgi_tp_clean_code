package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceRemoverPointsService {

    private final DrivingLicenceFinderService serviceFinder;

    private final InMemoryDatabase database;

    public DrivingLicence removePoints(int points, UUID id) throws ResourceNotFoundException {
        val licenceDrivingOpt = serviceFinder.findById(id);
        if (licenceDrivingOpt.isEmpty()) {
            throw new ResourceNotFoundException("La licence n'existe pas");
        }
        var licenceDriving = licenceDrivingOpt.get();
        var currentPoints = licenceDriving.getAvailablePoints() - points;
        if (currentPoints < 0) {
            currentPoints = 0;
        }
        licenceDriving = DrivingLicence.builder()
                .driverSocialSecurityNumber(licenceDriving.getDriverSocialSecurityNumber())
                .availablePoints(currentPoints)
                .id(id)
                .build();
        return database.save(id, licenceDriving);
    }

}

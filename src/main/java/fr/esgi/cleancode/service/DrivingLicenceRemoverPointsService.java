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

    public DrivingLicence removePointsById(int pointsToRemove, UUID id) throws ResourceNotFoundException {
        val licenceDrivingOpt = serviceFinder.findById(id);
        if (licenceDrivingOpt.isEmpty()) {
            throw new ResourceNotFoundException("La licence n'existe pas");
        }
        var licenceDriving = licenceDrivingOpt.get();
        var currentPoints = removePoints(pointsToRemove, licenceDriving.getAvailablePoints());
        licenceDriving = licenceDriving.withAvailablePoints(currentPoints);
        return database.save(id, licenceDriving);
    }

    public int removePoints(int pointToRemove, int currentPoint) {
        var newPoints = currentPoint - pointToRemove;
        if(newPoints < 0) {
            newPoints = 0;
        }
        return newPoints;
    }

}

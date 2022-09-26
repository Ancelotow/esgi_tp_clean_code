package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class DrivingLicenceCreatorService {

    private final InMemoryDatabase database;

    private final DrivingLicenceIdGenerationService serviceGeneratorId;

    private final DrivingLicenceValidatorService serviceValidator ;

    public DrivingLicence save(DrivingLicence drivingLicence) throws InvalidDriverSocialSecurityNumberException {
        val id = serviceGeneratorId.generateNewDrivingLicenceId();
        serviceValidator.isValidSSNumber(drivingLicence);
        return database.save(id, drivingLicence);
    }

}

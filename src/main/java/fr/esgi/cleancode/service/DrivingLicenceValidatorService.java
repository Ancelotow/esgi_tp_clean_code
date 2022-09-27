package fr.esgi.cleancode.service;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.regex.Pattern;

public class DrivingLicenceValidatorService {

    public boolean isValidSSNumber(DrivingLicence drivingLicence) {
        String securitySocialNumber = drivingLicence.getDriverSocialSecurityNumber();
        if(securitySocialNumber == null) {
            return false;
        } else if(securitySocialNumber.isEmpty()) {
            return false;
        } else if(securitySocialNumber.length() != 15) {
            return false;
        } else return Pattern.matches("\\d*", securitySocialNumber);
    }


}

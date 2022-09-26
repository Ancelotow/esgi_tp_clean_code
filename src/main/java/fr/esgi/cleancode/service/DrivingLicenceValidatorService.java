package fr.esgi.cleancode.service;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.regex.Pattern;

public class DrivingLicenceValidatorService {

    public boolean isValidSSNumber(DrivingLicence drivingLicence) throws InvalidDriverSocialSecurityNumberException {
        String securitySocialNumber = drivingLicence.getDriverSocialSecurityNumber();
        if(securitySocialNumber == null) {
            throw new InvalidDriverSocialSecurityNumberException("Le numéro de sécurité social est null");
        } else if(securitySocialNumber.isEmpty()) {
            throw new InvalidDriverSocialSecurityNumberException("Le numéro de sécurité social est vide");
        } else if(securitySocialNumber.length() != 15) {
            throw new InvalidDriverSocialSecurityNumberException("Le numéro de sécurité social doit contenir 15 caractères");
        } else if (!Pattern.matches("\\d*", securitySocialNumber)) {
            throw new InvalidDriverSocialSecurityNumberException("Le numéro de sécurité social doit contenir uniquement des chiffres");
        } else {
            return true;
        }
    }


}

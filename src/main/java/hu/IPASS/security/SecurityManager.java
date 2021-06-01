package hu.IPASS.security;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.GebruikerData;

import java.io.Serializable;

public class SecurityManager implements Serializable {
    private static SecurityManager securityManager;

    public static SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = new SecurityManager();
        }
        return securityManager;
    }

    private SecurityManager() {
    }

    public String validateLogin(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        var deGebruiker = GebruikerData.getGebruikerData().getGebruiker(email);
        if (deGebruiker == null) {
            return null;
        }
        if (deGebruiker.checkWachtwoord(password)) {
            return deGebruiker.getRole();
        } else {
            return null;
        }
    }
}

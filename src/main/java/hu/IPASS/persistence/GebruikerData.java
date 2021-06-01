package hu.IPASS.persistence;

import hu.IPASS.domeinklassen.Gebruiker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GebruikerData  implements Serializable {
    private List<Gebruiker> alleGebruikers = new ArrayList<>();

    private static GebruikerData gebruikerData = new GebruikerData();

    public static GebruikerData getGebruikerData() {
        if (gebruikerData == null) {
            gebruikerData = new GebruikerData();
        }
        return gebruikerData;
    }

    public static void setGebruikerData(GebruikerData gd) {
        gebruikerData = gd;
    }

    private GebruikerData() {
    }

    public boolean addGebruiker(Gebruiker g) {
        if (!alleGebruikers.contains(g)) {
            alleGebruikers.add(g);
            return true;
        }
        return false;
    }

    public List<Gebruiker> getAlleGebruikers() {
        return alleGebruikers;
    }

    public Gebruiker getGebruiker(String em) {
        for (Gebruiker geb : alleGebruikers) {
            if (geb.getEmailAdres().equals(em)) {
                return geb;
            }
        }
        return null;
    }
}

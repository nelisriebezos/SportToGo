package hu.IPASS.persistence;

import hu.IPASS.domeinklassen.Gebruiker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GebruikerData  implements Serializable {
    private List<Gebruiker> alleGebruikers = new ArrayList<>();

    private static GebruikerData gebruikerData;

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

    public void voegGebruikerToe(Gebruiker g) {
        alleGebruikers.add(g);
    }

    public List<Gebruiker> getAlleGebruikers() {
        return new ArrayList<>(alleGebruikers);
    }

    public Gebruiker getGebruiker(String em) {
        for (Gebruiker geb : alleGebruikers) {
            if (geb.getEmailadres().equals(em)) {
                return geb;
            }
        }
        return null;
    }
}

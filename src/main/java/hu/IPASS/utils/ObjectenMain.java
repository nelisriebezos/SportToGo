package hu.IPASS.utils;

import hu.IPASS.domeinklassen.*;
import hu.IPASS.persistence.OefeningTypeData;
import hu.IPASS.persistence.PersistenceManager;

import java.time.LocalDate;
import java.time.LocalTime;

public class ObjectenMain {
    public static void main( String[] args ) {
        Gebruiker geb1 = new Gebruiker("test", "test", "test", "gebruiker");
        Gebruiker geb2 = new Gebruiker("admin", "admin@email.nl", "admin", "admin");

        OefeningType squats = new OefeningType("squats", " Zet je voeten op schouderbreedte en strek je armen voor je uit. Houd je bovenlichaam stabiel terwijl je jezelf langzaam laat zakken. Kom na een paar seconden weer omhoog");
        OefeningType crunches = new OefeningType("crunches", "Lig op je rug met je knieën omhoog. Zonder hulp van je armen of benen breng je hoofd richting je knieën. Beweeg ongeveer 10 tot 20 cm omhoog voordat je jezelf laat zakken. Herhaal dit snel achter elkaar");
        OefeningType pushups = new OefeningType("Push-Ups", "Lig plat op de grond, plaats je handen naast je borstkas en duw je zelf omhoog. Leun op je tenen of op je knieën, houd je rug goed recht. Laat jezelf weer rustig zakken.");
        OefeningType plank = new OefeningType("Planks", "Lig plat op de grond, plaats je handen naast je borstkas en duw je zelf omhoog. Leun op je tenen en houd deze houding vast.");
        OefeningType bicepscurl = new OefeningType("Biceps curl", "Pak een gewicht en houd deze in het midden van je palm. Houd je bovenarm strak langs je licham en beweeg het gewicht omhoog en omlaag. Zorg ervoor dat je bovenarm niet meebeweegt");
        OefeningType superman = new OefeningType("Superman", "Ga op de grond liggen en leg je armen voor je uit plat op de grond. Zorg ervoor dat je ellebogen een 90 graden hoek hebben. Beweeg je armen samen recht vooruit en til je benen lichtelijk op.");
        OefeningType triceps = new OefeningType("Triceps curl", "Pak een gewicht en houd je arm naast je oor recht omhoog. Laat het gewicht langzaam naar achter zakken, let erop dat je alleen je bovenarm gebruikt om het gewicht te bewegen.");

        geb1.addSchema(new Schema("onderlichaam"));
        geb1.addSchema(new Schema("bovenlichaam"));

        geb1.getSchema("onderlichaam").addOefening(new Oefening(40, 10, squats));
        geb1.getSchema("bovenlichaam").addOefening(new Oefening(10, 20, crunches));

        geb1.addSessie(new Sessie("sessie 1", LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(14, 0)));
        geb1.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), LocalTime.of(13, 0), LocalTime.of(14, 0)));

        geb1.getSessie("sessie 1").setSchema(geb1.getSchema("onderlichaam"));
        geb1.getSessie("sessie 2").setSchema(geb1.getSchema("bovenlichaam"));

        Gebruiker.registreerGebruiker(geb1);
        Gebruiker.registreerGebruiker(geb2);

        OefeningTypeData.getOefeningTypeData().addOefeningType(squats);
        OefeningTypeData.getOefeningTypeData().addOefeningType(crunches);
        OefeningTypeData.getOefeningTypeData().addOefeningType(pushups);
        OefeningTypeData.getOefeningTypeData().addOefeningType(plank);
        OefeningTypeData.getOefeningTypeData().addOefeningType(bicepscurl);
        OefeningTypeData.getOefeningTypeData().addOefeningType(superman);
        OefeningTypeData.getOefeningTypeData().addOefeningType(triceps);

        PersistenceManager.sendOefeningTypeToAzure();
        PersistenceManager.sendUsersToAzure();
        PersistenceManager.loadOefeningTypeFromAzure();
        PersistenceManager.loadUserFromAzure();
    }
}

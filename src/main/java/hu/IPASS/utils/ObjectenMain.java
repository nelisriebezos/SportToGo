package hu.IPASS.utils;

import com.azure.storage.blob.BlobClient;
import hu.IPASS.domeinklassen.*;
import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;
import hu.IPASS.persistence.PersistenceManager;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ObjectenMain
{
    public static void main( String[] args ) {
        Gebruiker geb1 = new Gebruiker("Niels Riezebos", "niels.riezebos@student.hu.nl", "w", "gebruiker");
        Gebruiker geb2 = new Gebruiker("admin", "admin@email.nl", "admin", "admin");

        OefeningType squats = new OefeningType("squats", "test OefeningType squats");
        OefeningType crunches = new OefeningType("crunches", "test OefeningType crunches");

        geb1.addSchema(new Schema("onderlichaam"));
        geb1.addSchema(new Schema("bovenlichaam"));
        geb2.addSchema(new Schema("onderlichaam"));
        geb2.addSchema(new Schema("bovenlichaam"));

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

        PersistenceManager.sendOefeningTypeToAzure();
        PersistenceManager.sendUsersToAzure();
        PersistenceManager.loadOefeningTypeFromAzure();
        PersistenceManager.loadUserFromAzure();
    }
}

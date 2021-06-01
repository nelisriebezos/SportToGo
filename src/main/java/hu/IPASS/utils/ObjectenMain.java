package hu.IPASS.utils;

import hu.IPASS.domeinklassen.*;
import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;
import hu.IPASS.persistence.PersistenceManager;

import java.time.LocalDate;

public class ObjectenMain
{
    public static void main( String[] args ) {
        Gebruiker geb1 = new Gebruiker("Niels Riezebos", "niels.riezebos@student.hu.nl", "wachtwoord1", "gebruiker");
        Gebruiker geb2 = new Gebruiker("Bruus Riezebos", "bruus.riezebos@student.hu.nl", "wachtwoord2", "gebruiker");
        Gebruiker geb3 = new Gebruiker("britte Riezebos", "britte.riezebos@student.hu.nl", "wachtwoord3", "gebruiker");
        Gebruiker geb4 = new Gebruiker("nelis riebezos", "niels@ding.nl", "wachtwoord1", "gebruiker");

        OefeningType squats = new OefeningType("squats", "test OefeningType squats");
        OefeningType crunches = new OefeningType("crunches", "test OefeningType crunches");
        OefeningType armcurl = new OefeningType("arm curl", "test Oefeningtype arm curl");

        geb1.addSchema(new Schema("onderlichaam"));
        geb1.addSchema(new Schema("bovenlichaam"));
        geb2.addSchema(new Schema("onderlichaam"));
        geb2.addSchema(new Schema("bovenlichaam"));
        geb4.addSchema(new Schema("onderlichaam"));
        geb4.addSchema(new Schema("bovenlichaam"));

        geb1.getSchema("onderlichaam").addOefening(new Oefening(40, 10, squats));
        geb1.getSchema("bovenlichaam").addOefening(new Oefening(10, 20, crunches));
        geb2.getSchema("onderlichaam").addOefening(new Oefening(10, 20, squats));
        geb2.getSchema("bovenlichaam").addOefening(new Oefening(15, 12, armcurl));
        geb4.getSchema("onderlichaam").addOefening(new Oefening(40, 10, squats));
        geb4.getSchema("bovenlichaam").addOefening(new Oefening(10, 20, crunches));


        geb1.addSessie(new Sessie("sessie 1", LocalDate.now(), "13:00", "14:00"));
        geb1.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), "13:00", "14:00"));
        geb2.addSessie(new Sessie("sessie 1", LocalDate.now(), "14:00", "15:00"));
        geb2.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), "12:00", "14:00"));
        geb4.addSessie(new Sessie("sessie 1", LocalDate.now(), "13:00", "14:00"));
        geb4.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), "13:00", "14:00"));


        geb1.getSessie("sessie 1").setSchema(geb1.getSchema("onderlichaam"));
        geb1.getSessie("sessie 2").setSchema(geb1.getSchema("bovenlichaam"));
        geb2.getSessie("sessie 1").setSchema(geb2.getSchema("onderlichaam"));
        geb2.getSessie("sessie 2").setSchema(geb2.getSchema("bovenlichaam"));
        geb4.getSessie("sessie 1").setSchema(geb1.getSchema("onderlichaam"));
        geb4.getSessie("sessie 2").setSchema(geb1.getSchema("bovenlichaam"));

        GebruikerData.getGebruikerData().addGebruiker(geb1);
        GebruikerData.getGebruikerData().addGebruiker(geb2);
        GebruikerData.getGebruikerData().addGebruiker(geb3);
        GebruikerData.getGebruikerData().addGebruiker(geb4);

        OefeningTypeData.getOefeningTypeData().addOefeningType(squats);
        OefeningTypeData.getOefeningTypeData().addOefeningType(crunches);
        OefeningTypeData.getOefeningTypeData().addOefeningType(armcurl);

        System.out.println(GebruikerData.getGebruikerData().getAlleGebruikers());
        System.out.println(OefeningTypeData.getOefeningTypeData().getAlleOefeningTypes());

        System.out.println();

        System.out.println(GebruikerData.getGebruikerData().getGebruiker("niels@ding.nl"));

        System.out.println();

        PersistenceManager.sendOefeningTypeToAzure();
        PersistenceManager.sendUsersToAzure();
        PersistenceManager.loadOefeningTypeFromAzure();
        PersistenceManager.loadUserFromAzure();

        System.out.println(GebruikerData.getGebruikerData().getAlleGebruikers());
        System.out.println(OefeningTypeData.getOefeningTypeData().getAlleOefeningTypes());
    }
}

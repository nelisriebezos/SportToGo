package hu.IPASS.utils;

import hu.IPASS.domeinklassen.*;
import hu.IPASS.persistence.PersistenceManager;

import java.time.LocalDate;

public class ObjectenMain
{
    public static void main( String[] args ) {
        Gebruiker geb1 = new Gebruiker("Niels Riezebos", "niels.riezebos@student.hu.nl", "wachtwoord1", 1);
        Gebruiker geb2 = new Gebruiker("Bruus Riezebos", "bruus.riezebos@student.hu.nl", "wachtwoord2", 2);
        Gebruiker geb3 = new Gebruiker("britte Riezebos", "britte.riezebos@student.hu.nl", "wachtwoord3", 3);

        OefeningType squats = new OefeningType("squats", "test OefeningType squats");
        OefeningType crunches = new OefeningType("crunches", "test OefeningType crunches");
        OefeningType armcurl = new OefeningType("arm curl", "test Oefeningtype arm curl");

        geb1.addSchema(new Schema("onderlichaam"));
        geb1.addSchema(new Schema("bovenlichaam"));
        geb2.addSchema(new Schema("onderlichaam"));
        geb2.addSchema(new Schema("bovenlichaam"));

        geb1.getSchema("onderlichaam").addOefening(new Oefening(40, 10, squats));
        geb1.getSchema("bovenlichaam").addOefening(new Oefening(10, 20, crunches));
        geb2.getSchema("onderlichaam").addOefening(new Oefening(10, 20, squats));
        geb2.getSchema("bovenlichaam").addOefening(new Oefening(15, 12, armcurl));

        geb1.addSessie(new Sessie("sessie 1", LocalDate.now(), "13:00", "14:00"));
        geb1.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), "13:00", "14:00"));
        geb2.addSessie(new Sessie("sessie 1", LocalDate.now(), "14:00", "15:00"));
        geb2.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), "12:00", "14:00"));

        geb1.getSessie("sessie 1").setSchema(geb1.getSchema("onderlichaam"));
        geb1.getSessie("sessie 2").setSchema(geb1.getSchema("bovenlichaam"));
        geb2.getSessie("sessie 1").setSchema(geb2.getSchema("onderlichaam"));
        geb2.getSessie("sessie 2").setSchema(geb2.getSchema("bovenlichaam"));


//        PersistenceManager.getPM().sendUserToAzure(geb1);
//        PersistenceManager.getPM().sendUserToAzure(geb2);
//        PersistenceManager.getPM().sendUserToAzure(geb3);

        PersistenceManager.getPM().loadUsersFromAzure();
//        System.out.println(PersistenceManager.getPM().getGebruikerList());

    }
}
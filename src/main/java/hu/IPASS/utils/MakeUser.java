package hu.IPASS.utils;

import hu.IPASS.domeinklassen.*;

import java.time.LocalDate;

public class MakeUser {
    public static Gebruiker makeUserData() {
        Gebruiker geb1 = new Gebruiker("Niels Riezebos", "niels@ding.nl", "wachtwoord1");

        OefeningType squats = new OefeningType("squats", "test OefeningType squats");
        OefeningType crunches = new OefeningType("crunches", "test OefeningType crunches");

        geb1.addSchema(new Schema("onderlichaam"));
        geb1.addSchema(new Schema("bovenlichaam"));

        geb1.getSchema("onderlichaam").addOefening(new Oefening(40, 10, squats));
        geb1.getSchema("bovenlichaam").addOefening(new Oefening(10, 20, crunches));

        geb1.addSessie(new Sessie("sessie 1", LocalDate.now(), "13:00", "14:00"));
        geb1.addSessie(new Sessie("sessie 2", LocalDate.now().plusDays(1), "13:00", "14:00"));

        geb1.getSessie("sessie 1").setSchema(geb1.getSchema("onderlichaam"));
        geb1.getSessie("sessie 2").setSchema(geb1.getSchema("bovenlichaam"));

        return geb1;
    }
}

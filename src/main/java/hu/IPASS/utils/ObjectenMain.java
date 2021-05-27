package hu.IPASS.utils;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.domeinklassen.Schema;
import hu.IPASS.persistence.PersistenceManager;

public class ObjectenMain
{
    public static void main( String[] args ) {
        Gebruiker geb1 = new Gebruiker("Niels Riezebos", "niels.riezebos@student.hu.nl", "wachtwoord1");
        Gebruiker geb2 = new Gebruiker("Bruus Riezebos", "bruus.riezebos@student.hu.nl", "wachtwoord2");
        Gebruiker geb3 = new Gebruiker("britte Riezebos", "britte.riezebos@student.hu.nl", "wachtwoord3");

        OefeningType squats = new OefeningType("squats", "test OefeningType squats");
        OefeningType crunches = new OefeningType("crunches", "test OefeningType crunches");

        geb1.addSchema(new Schema("onderlichaam"));
        geb1.addSchema(new Schema("bovenlichaam"));

        geb1.getSchema("onderlichaam").addOefening(new Oefening(40, 10, squats));
        geb1.getSchema("bovenlichaam").addOefening(new Oefening(10, 20, crunches));

    }
}
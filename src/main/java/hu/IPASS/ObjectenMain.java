package hu.IPASS;

import hu.IPASS.Domeinklassen.Gebruiker;
import hu.IPASS.Domeinklassen.Oefening;
import hu.IPASS.Domeinklassen.OefeningType;
import hu.IPASS.Domeinklassen.Schema;
import hu.IPASS.Domeinklassen.Sessie;

public class ObjectenMain
{
    public static void main( String[] args ) {
        Gebruiker geb1 = new Gebruiker("Niels Riezebos", "ding@ding.nl", "wachtwoord");

        OefeningType squats = new OefeningType("squats", "test OefeningType squats");
        OefeningType crunches = new OefeningType("crunches", "test OefeningType crunches");

        geb1.addSchema(new Schema("testSchema1"));
        geb1.addSchema(new Schema("testSchema2"));

        geb1.getSchema("testSchema1").addOefening(new Oefening(40, 10, squats));
        geb1.getSchema("testSchema1").addOefening(new Oefening(10, 20, crunches));

    }
}

package hu.IPASS;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.domeinklassen.Schema;
import hu.IPASS.persistence.PersistenceManager;

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

        PersistenceManager.getPM().sendUserToAzure(geb1);
    }
}

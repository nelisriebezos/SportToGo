package hu.IPASS;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.domeinklassen.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class domeintest {
    Gebruiker g1;
    Schema s1;
    Schema s2;
    Oefening o1;
    Oefening o2;
    OefeningType ot1;
    OefeningType ot2;

    @BeforeEach
    public void initialize() {
        g1 = new Gebruiker("naam1", "eadres1", "wachtwoord1");
        s1 = new Schema("schema1");
        s2 = new Schema("schema2");
        ot1 = new OefeningType("squats", "beschrijving1");
        ot2 = new OefeningType("situps", "beschrijving2");
        o1 = new Oefening(20, 10, ot1);
        o2 = new Oefening(30, 0, ot2);

        g1.addSchema(s1);
        g1.getSchema("schema1").addOefening(o1);
    }

    @Test
    public void wachtwoordVeranderdNaarHetzelfde() {
        assertFalse(g1.setWachtwoord("wachtwoord1"), "Returned true, moet false zijn");
    }

    @Test
    public void wachtwoordVeranderdNaarAnders() {
        assertTrue(g1.setWachtwoord("wachtwoordnieuw"), "returned false, moet true zijn");
    }

    @Test
    public void voegZelfdeSchemaToe() {
        assertFalse(g1.addSchema(s1), "returned true, moet false zijn");
    }

    @Test
    public void voegNieuwSchemaToe() {
        assertTrue(g1.addSchema(s2), "returned false, moet true zijn");
    }

    @Test
    public void verkeerdSchemaOproepen() {
        assertNull(g1.getSchema("verkeerdeNaam"), "returned een Schema, moet null returnen");
    }

    @Test
    public void juistSchemaOproepen() {
        assertEquals(s1, g1.getSchema("schema1"), "returned null, moet s1 returnen");
    }

    @Test
    public void addzelfdeOefeningAanSchema() {
        assertFalse(g1.getSchema("schema1").addOefening(o1), "returned true, moet false zijn");
    }

    @Test
    public void addNieuweOefeningAanSchema() {
        assertTrue(g1.getSchema("schema1").addOefening(o2), "returned false, moet true zijn");
    }
}

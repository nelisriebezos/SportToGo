package hu.IPASS.domeinklassen;

import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class domeintest {
    OefeningTypeData otd;
    GebruikerData gd;
    Gebruiker g1;
    Schema s1;
    Schema s2;
    Oefening o1;
    Oefening o2;
    OefeningType ot1;
    OefeningType ot2;
    Sessie se1;

    @BeforeEach
    public void initialize() {
        otd = new OefeningTypeData();
        gd = new GebruikerData();
        g1 = new Gebruiker("naam", "adres", "wachtwoord", "gebruiker");
        s1 = new Schema("schema1");
        s2 = new Schema("schema2");
        ot1 = new OefeningType("squats", "beschrijving1");
        ot2 = new OefeningType("situps", "beschrijving2");
        o1 = new Oefening(20, 10, ot1);
        o2 = new Oefening(30, 0, ot2);
        se1 = new Sessie("sessie", LocalDate.now(), "1", "1");

        g1.addSchema(s1);
        g1.getSchema("schema1").addOefening(o1);
    }

//    OefeningTypeData
    @Test
    public void addOefeningTypeBestaatAl() {
        otd.addOefeningType(ot1);
        assertFalse(otd.addOefeningType(ot1));
    }

//    Gebruiker
    @Test
    public void voegZelfdeSchemaToe() {
        assertFalse(g1.addSchema(s1));
    }

    @Test
    public void voegNieuwSchemaToe() {
        assertTrue(g1.addSchema(s2));
    }

    @Test
    public void getSchemaVerkeerdeNaam() {
        assertNull(g1.getSchema("verkeerdeNaam"));
    }

    @Test
    public void getSchemaJuisteNaam() {
        assertEquals(s1, g1.getSchema("schema1"));
    }

    @Test
    public void getSessieJuisteNaam() {
        assertEquals(se1, g1.getSessie("sessie"));
    }

    @Test
    public void getSessieVerkeerdeNaam() {
        assertNull(g1.getSchema("verkeerdenaam"));
    }

    @Test
    public void setWachtwoordNieuwWachtwoord() {
        assertTrue(g1.setWachtwoord("wachtwoord2"));
    }

    @Test
    public void setWachtwoordoudWachtwoord() {
        assertFalse(g1.setWachtwoord("wachtwoord"));
    }

    @Test
    public void getUserByNameJuisteNaam() {
        assertEquals(g1, Gebruiker.getUserByName("naam"));
    }

    @Test
    public void getUserByNameVerkeerdeNaam() {
        assertNull(Gebruiker.getUserByName(""));
    }

    @Test
    public void registreerGebruikerBestaatAl() {
        Gebruiker.registreerGebruiker(g1);
        assertFalse(Gebruiker.registreerGebruiker(g1));
    }

    @Test
    public void registreerGebruikerBestaatNiet() {
        assertTrue(Gebruiker.registreerGebruiker(g1));
    }

    @Test
    public void validateLoginVerkeerdeNaam() {
        assertNull(Gebruiker.validateLogin("verkeerdenaam", "wachtwoord"));
    }

    @Test
    public void validateLoginVerkeerdWachtwoord() {
        assertNull(Gebruiker.validateLogin("naam", "wachtwoord1"));
    }

    @Test
    public void validateLoginJuisteNaam() {
        assertEquals(g1.getRol(), Gebruiker.validateLogin("naam", "wachtwoord"));
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

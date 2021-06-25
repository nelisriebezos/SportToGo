package hu.IPASS.domeinklassen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.IPASS.persistence.GebruikerData;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Iterator;

public class Gebruiker implements Principal ,Serializable {
    private String gebruikernaam;
    private String emailadres;
    private String wachtwoord;
    private String rol;
    ArrayList<Schema> schemaLijst = new ArrayList<>();
    ArrayList<Sessie> sessieLijst = new ArrayList<>();

    public Gebruiker(String nm, String ea, String ww, String rol) {
        this.gebruikernaam = nm;
        this.emailadres = ea;
        this.wachtwoord = ww;
        this.rol = rol;
    }

    public Gebruiker() {
    }

    public boolean addSchema(Schema s) {
        if (!this.schemaLijst.contains(s)) {
            this.schemaLijst.add(s);
            return true;
        }
        return false;
    }

    public Schema getSchema(String nm) {
        for (Schema s : this.schemaLijst) {
            if (s.getNaam().equals(nm)) {
                return s;
            }
        }
        return null;
    }

    public Sessie getSessie(String nm) {
        for (Sessie s : this.sessieLijst) {
            if (s.getNaam().equals(nm)) {
                return s;
            }
        }
        return null;
    }

    public boolean addSessie(Sessie s) {
        for (Sessie sessie : sessieLijst) {
            if (sessie.equals(s)) {
                return false;
            }
        }
        sessieLijst.add(s);
        return true;
    }

    public boolean verwijderSessie(Sessie s) {
        Iterator<Sessie> itr = sessieLijst.iterator();
        while(itr.hasNext()) {
            Sessie sessie = itr.next();
            if (sessie.equals(s)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    public boolean verwijderSchema(Schema s) {
        Iterator<Schema> itr = schemaLijst.iterator();
        while(itr.hasNext()) {
            Schema schema = itr.next();
            if (schema.equals(s)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }


    public String getGebruikernaam() {
        return gebruikernaam;
    }

    public void setGebruikernaam(String gebruikernaam) {
        this.gebruikernaam = gebruikernaam;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public boolean setWachtwoord(String wachtwoord) {
        if (wachtwoord.equals(this.wachtwoord)) {
            return false;
        }
        this.wachtwoord = wachtwoord;
        return true;
    }

    public boolean checkPassword(String password) {
        return this.wachtwoord.equals(password);
    }

    @JsonIgnore
    public ArrayList<Schema> getSchemaLijst() {
        return schemaLijst;
    }

    public void setSchemaLijst(ArrayList<Schema> schemaLijst) {
        this.schemaLijst = schemaLijst;
    }

    @JsonIgnore
    public ArrayList<Sessie> getSessieLijst() {
        return sessieLijst;
    }

    public void setSessieLijst(ArrayList<Sessie> sessieLijst) {
        this.sessieLijst = sessieLijst;
    }

    @Override
    public String getName() {
        return gebruikernaam;
    }

    public String getRol() {
        return rol;
    }

    public static Gebruiker getUserByName(String gebruikernaam) {
        return GebruikerData.getGebruikerData().getAlleGebruikers().stream().filter(user -> user.getName().equals(gebruikernaam)).findFirst().orElse(null);
    }

    public static boolean registreerGebruiker(Gebruiker g) {
        for (Gebruiker geb : GebruikerData.getGebruikerData().getAlleGebruikers()) {
            if (geb.getEmailadres().equals(g.getEmailadres())) {
                return false;
            }
        }
        GebruikerData.getGebruikerData().voegGebruikerToe(g);
        return true;
    }

    public static String validateLogin(String gebruikernaam, String password) {
        if (gebruikernaam == null || gebruikernaam.isBlank() || password == null || password.isBlank()) return null;
        Gebruiker user = getUserByName(gebruikernaam);
        if (user == null) return null;
        return user.checkPassword(password) ? user.getRol() : null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gebruiker)) return false;
        Gebruiker gebruiker = (Gebruiker) o;
        return Objects.equals(getGebruikernaam(), gebruiker.getGebruikernaam()) &&
                Objects.equals(getEmailadres(), gebruiker.getEmailadres());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikernaam);
    }

    @Override
    public String toString() {
        return "Gebruiker{" +
                "gebruikernaam='" + gebruikernaam + '\'' +
                ", emailadres='" + emailadres + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}

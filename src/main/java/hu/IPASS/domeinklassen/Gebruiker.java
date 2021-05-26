package hu.IPASS.domeinklassen;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;

public class Gebruiker implements Serializable {
    private String naam;
    private String emailAdres;
    private String wachtwoord;
    ArrayList<Schema> schemaLijst = new ArrayList<>();
    ArrayList<Sessie> sessieLijst = new ArrayList<>();

    public Gebruiker(String nm, String ea, String ww) {
        this.naam = nm;
        this.emailAdres = ea;
        this.wachtwoord = ww;
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
        if (!this.sessieLijst.contains(s)) {
            this.sessieLijst.add(s);
            return true;
        }
        return false;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }

    public boolean setWachtwoord(String wachtwoord) {
        if (wachtwoord.equals(this.wachtwoord)) {
            return false;
        }
        this.wachtwoord = wachtwoord;
        return true;
    }

    public ArrayList<Schema> getSchemaLijst() {
        return schemaLijst;
    }

    public void setSchemaLijst(ArrayList<Schema> schemaLijst) {
        this.schemaLijst = schemaLijst;
    }

    public ArrayList<Sessie> getSessieLijst() {
        return sessieLijst;
    }

    public void setSessieLijst(ArrayList<Sessie> sessieLijst) {
        this.sessieLijst = sessieLijst;
    }

    @Override
    public String toString() {
        return "Gebruiker{" +
                "naam='" + naam + '\'' +
                ", emailAdres='" + emailAdres + '\'' +
                ", wachtwoord='" + wachtwoord + '\'' +
                ", schemaLijst=" + schemaLijst +
                ", sessieLijst=" + sessieLijst +
                '}';
    }
}

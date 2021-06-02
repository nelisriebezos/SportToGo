package hu.IPASS.domeinklassen;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gebruiker implements Serializable {
    private String naam;
    private String emailAdres;
    private String wachtwoord;
    private String role;
    ArrayList<Schema> schemaLijst = new ArrayList<>();
    ArrayList<Sessie> sessieLijst = new ArrayList<>();

    public Gebruiker(String nm, String ea, String ww, String role) {
        this.naam = nm;
        this.emailAdres = ea;
        this.wachtwoord = ww;
        this.role = role;
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

    public boolean checkWachtwoord(String wachtwoord) {
        if (this.wachtwoord.equals(wachtwoord)) {
            return true;
        }
        return false;
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

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gebruiker)) return false;
        Gebruiker gebruiker = (Gebruiker) o;
        return Objects.equals(getNaam(), gebruiker.getNaam()) &&
                Objects.equals(getEmailAdres(), gebruiker.getEmailAdres()) &&
                Objects.equals(wachtwoord, gebruiker.wachtwoord) &&
                Objects.equals(getSchemaLijst(), gebruiker.getSchemaLijst()) &&
                Objects.equals(getSessieLijst(), gebruiker.getSessieLijst());
    }

    @Override
    public String toString() {
        return
                "naam='" + naam + '\'' +
                ", emailAdres='" + emailAdres + '\'' +
                ", wachtwoord='" + wachtwoord + '\'' +
                ", schemaLijst=" + schemaLijst +
                ", sessieLijst=" + sessieLijst +
                '}';
    }
}

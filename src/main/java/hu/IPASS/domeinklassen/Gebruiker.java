package hu.IPASS.domeinklassen;

import java.util.ArrayList;

public class Gebruiker {
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

    public void addSchema(Schema s) {
        if (!this.schemaLijst.contains(s)) {
            this.schemaLijst.add(s);
        }
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

    public void addSessie(Sessie s) {
        if (!this.sessieLijst.contains(s)) {
            this.sessieLijst.add(s);
        }
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

    public String getWachtwoord() {
        return wachtwoord;
    }

    public boolean setWachtwoord(String wachtwoord) {
        if (wachtwoord == this.wachtwoord) {
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

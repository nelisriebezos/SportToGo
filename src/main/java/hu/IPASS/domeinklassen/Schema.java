package hu.IPASS.domeinklassen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Schema implements Serializable {
    private String naam;
    private Sessie sessie;
    private Gebruiker gebruiker;
    ArrayList<Oefening> oefeningLijst = new ArrayList<>();

    public Schema(String nm) {
        this.naam = nm;
    }

    public boolean addOefening(Oefening o) {
        if (!this.oefeningLijst.contains(o)) {
            this.oefeningLijst.add(o);
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

    public Sessie getSessie() {
        return sessie;
    }

    public void setSessie(Sessie sessie) {
        this.sessie = sessie;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public ArrayList<Oefening> getOefeningLijst() {
        return oefeningLijst;
    }

    public void setOefeningLijst(ArrayList<Oefening> oefeningLijst) {
        this.oefeningLijst = oefeningLijst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema)) return false;
        Schema schema = (Schema) o;
        return Objects.equals(getNaam(), schema.getNaam()) &&
                Objects.equals(getSessie(), schema.getSessie()) &&
                Objects.equals(getGebruiker(), schema.getGebruiker()) &&
                Objects.equals(getOefeningLijst(), schema.getOefeningLijst());
    }


    @Override
    public String toString() {
        return naam;
    }
}

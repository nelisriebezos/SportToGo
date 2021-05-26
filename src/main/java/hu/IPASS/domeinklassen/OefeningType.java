package hu.IPASS.domeinklassen;

import java.io.Serializable;
import java.util.ArrayList;

public class OefeningType implements Serializable {
    private String naam;
    private String beschrijving;
    ArrayList<Oefening> oefeningLijst = new ArrayList<>();

    public OefeningType(String nm, String bs) {
        this.naam = nm;
        this.beschrijving = bs;
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

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public ArrayList<Oefening> getOefeningLijst() {
        return oefeningLijst;
    }

    public void setOefeningLijst(ArrayList<Oefening> oefeningLijst) {
        this.oefeningLijst = oefeningLijst;
    }

    @Override
    public String toString() {
        return "OefeningType{" +
                "naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                '}';
    }
}

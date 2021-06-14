package hu.IPASS.domeinklassen;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

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

    @JsonIgnore
    public ArrayList<Oefening> getOefeningLijst() {
        return oefeningLijst;
    }

    public void setOefeningLijst(ArrayList<Oefening> oefeningLijst) {
        this.oefeningLijst = oefeningLijst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OefeningType)) return false;
        OefeningType that = (OefeningType) o;
        return Objects.equals(getNaam(), that.getNaam()) &&
                Objects.equals(getBeschrijving(), that.getBeschrijving()) &&
                Objects.equals(getOefeningLijst(), that.getOefeningLijst());
    }


    @Override
    public String toString() {
        return "OefeningType{" +
                "naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                '}';
    }
}

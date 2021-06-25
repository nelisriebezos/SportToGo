package hu.IPASS.domeinklassen;

import java.io.Serializable;
import java.util.Objects;

public class OefeningType implements Serializable {
    private String naam;
    private String beschrijving;

    public OefeningType(String nm, String bs) {
        this.naam = nm;
        this.beschrijving = bs;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OefeningType)) return false;
        OefeningType that = (OefeningType) o;
        return Objects.equals(getNaam(), that.getNaam());
    }


    @Override
    public String toString() {
        return "OefeningType{" +
                "naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                '}';
    }
}

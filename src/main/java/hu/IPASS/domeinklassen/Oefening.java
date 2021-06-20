package hu.IPASS.domeinklassen;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class Oefening implements Serializable {
    private int gewicht;
    private int setHoeveelheid;
    private OefeningType oefeningType;

    public Oefening(int gw, int sh, OefeningType ot) {
        this.gewicht = gw;
        this.setHoeveelheid = sh;
        this.oefeningType = ot;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public int getSetHoeveelheid() {
        return setHoeveelheid;
    }

    public void setSetHoeveelheid(int setHoeveelheid) {
        this.setHoeveelheid = setHoeveelheid;
    }

    public OefeningType getOefeningType() {
        return oefeningType;
    }

    public void setOefeningType(OefeningType oefeningType) {
        this.oefeningType = oefeningType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oefening)) return false;
        Oefening oefening = (Oefening) o;
        return getGewicht() == oefening.getGewicht() &&
                getSetHoeveelheid() == oefening.getSetHoeveelheid() &&
                Objects.equals(getOefeningType(), oefening.getOefeningType());
    }

    @Override
    public String toString() {
        return "Oefening{" +
                "gewicht=" + gewicht +
                ", setHoeveelheid=" + setHoeveelheid +
                ", oefeningType=" + oefeningType +
                '}';
    }
}

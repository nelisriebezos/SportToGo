package hu.IPASS.domeinklassen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Schema implements Serializable {
    private String naam;
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

    public ArrayList<Oefening> getOefeningLijst() {
        return oefeningLijst;
    }

    public void setOefeningLijst(ArrayList<Oefening> oefeningLijst) {
        this.oefeningLijst = oefeningLijst;
    }

    public boolean verwijderOefening(Oefening oef) {
        Iterator<Oefening> itr = oefeningLijst.iterator();
        while(itr.hasNext()) {
            Oefening oefening = itr.next();
            if (oefening.equals(oef)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema)) return false;
        Schema schema = (Schema) o;
        return Objects.equals(getNaam(), schema.getNaam());
    }


    @Override
    public String toString() {
        return naam;
    }
}

package hu.IPASS.domeinklassen;

import java.io.Serializable;
import java.util.Objects;

public class Sessie implements Serializable {
    private String naam;
    private String dag;
    private String beginTijd;
    private String eindTijd;
    private Gebruiker gebruiker;
    private Schema schema;

    public Sessie(String nm, String d, String bt, String et) {
        this.naam = nm;
        this.dag = d;
        this.beginTijd = bt;
        this.eindTijd = et;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    public String getBeginTijd() {
        return beginTijd;
    }

    public void setBeginTijd(String beginTijd) {
        this.beginTijd = beginTijd;
    }

    public String getEindTijd() {
        return eindTijd;
    }

    public void setEindTijd(String eindTijd) {
        this.eindTijd = eindTijd;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sessie)) return false;
        Sessie sessie = (Sessie) o;
        return Objects.equals(getNaam(), sessie.getNaam()) &&
                Objects.equals(getDag(), sessie.getDag()) &&
                Objects.equals(getBeginTijd(), sessie.getBeginTijd()) &&
                Objects.equals(getEindTijd(), sessie.getEindTijd()) &&
                Objects.equals(getGebruiker(), sessie.getGebruiker()) &&
                Objects.equals(getSchema(), sessie.getSchema());
    }

    @Override
    public String toString() {
        return "Sessie{" +
                "dag='" + dag + '\'' +
                ", beginTijd='" + beginTijd + '\'' +
                ", eindTijd='" + eindTijd + '\'' +
                '}';
    }
}

package hu.IPASS.domeinklassen;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Sessie implements Serializable {
    private String naam;
    private LocalDate dag;
    private LocalTime beginTijd;
    private LocalTime eindTijd;
    private Gebruiker gebruiker;
    private Schema schema;

    public Sessie(String nm, LocalDate d, LocalTime bt, LocalTime et) {
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

    public LocalDate getDag() {
        return dag;
    }

    public void setDag(LocalDate dag) {
        this.dag = dag;
    }

    public LocalTime getBeginTijd() {
        return beginTijd;
    }

    public void setBeginTijd(LocalTime beginTijd) {
        this.beginTijd = beginTijd;
    }

    public LocalTime getEindTijd() {
        return eindTijd;
    }

    public void setEindTijd(LocalTime eindTijd) {
        this.eindTijd = eindTijd;
    }

    @JsonIgnore
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

package hu.IPASS.domeinklassen;

public class Sessie {
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
    public String toString() {
        return "Sessie{" +
                "dag='" + dag + '\'' +
                ", beginTijd='" + beginTijd + '\'' +
                ", eindTijd='" + eindTijd + '\'' +
                '}';
    }
}

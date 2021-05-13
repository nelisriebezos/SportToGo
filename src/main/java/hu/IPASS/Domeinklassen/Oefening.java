package hu.IPASS.Domeinklassen;

public class Oefening {
    private int gewicht;
    private int setHoeveelheid;
    private Schema schema;
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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public OefeningType getOefeningType() {
        return oefeningType;
    }

    public void setOefeningType(OefeningType oefeningType) {
        this.oefeningType = oefeningType;
    }

    @Override
    public String toString() {
        return "Oefening{" +
                "gewicht=" + gewicht +
                ", setHoeveelheid=" + setHoeveelheid +
                '}';
    }
}

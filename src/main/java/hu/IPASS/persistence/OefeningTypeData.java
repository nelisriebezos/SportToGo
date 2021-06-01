package hu.IPASS.persistence;

import hu.IPASS.domeinklassen.OefeningType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OefeningTypeData implements Serializable {
    private List<OefeningType> alleOefeningTypes = new ArrayList<>();

    private static OefeningTypeData oefeningTypeData = new OefeningTypeData();

    public static OefeningTypeData getOefeningTypeData() {
        return oefeningTypeData;
    }

    public static void setOefeningTypeData(OefeningTypeData otd) {
        oefeningTypeData = otd;
    }

    private OefeningTypeData() {
    }

    public boolean addOefeningType(OefeningType ot) {
        if (!alleOefeningTypes.contains(ot)) {
            alleOefeningTypes.add(ot);
            return true;
        }
        return false;
    }

    public List<OefeningType> getAlleOefeningTypes() {
        return alleOefeningTypes;
    }

    public OefeningType getOefeningType(OefeningType ot) {
        for (OefeningType oefeningType : alleOefeningTypes) {
            if (oefeningType.equals(ot)) {
                return oefeningType;
            }
        }
        return null;
    }
}

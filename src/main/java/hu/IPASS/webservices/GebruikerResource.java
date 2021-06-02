package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.AbstractMap;

@Path("/gebruiker")
public class GebruikerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{email}")
    public Response getUserData(@PathParam("email") String email) {
        Gebruiker g = GebruikerData.getGebruikerData().getGebruiker(email);

        if (g == null)
            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("result", "Gebruiker niet gevonden") {}).build();
        return Response.ok(g).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{email}")
    public Response voegOefeningToe(@PathParam("email") String email,
                                    @FormParam("schemaKeuze") String schemakeuze,
                                    @FormParam("gewichtKeuze") int gewichtkeuze,
                                    @FormParam("setHoeveelheidKeuze") int setHoeveelheidKeuze) {

        Gebruiker g = GebruikerData.getGebruikerData().getGebruiker(email);

        if (g == null) {
            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("result", "Gebruiker niet gevonden") {}).build();
        }

        Oefening o = new Oefening(gewichtkeuze, setHoeveelheidKeuze, OefeningTypeData.getOefeningTypeData().getOefeningType("squats"));
        g.getSchema(schemakeuze).addOefening(o);

        if (!g.getSchema(schemakeuze).getOefeningLijst().contains(o)) {
            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("result", "Oefening niet gemaakt") {}).build();
        }
        return Response.ok(o).build();
    }
}

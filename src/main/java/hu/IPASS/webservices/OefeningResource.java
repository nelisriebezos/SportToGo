package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.domeinklassen.Schema;
import hu.IPASS.persistence.OefeningTypeData;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.AbstractMap;

@Path("/oefening")
public class OefeningResource {

    @GET
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOefeningData() {
        return Response.ok(OefeningTypeData.getOefeningTypeData().getAlleOefeningTypes()).build();
    }

    @POST
    @RolesAllowed({"gebruiker", "admin"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response maakOefeningAan(@Context SecurityContext sc,
                                    @FormParam("oefeningkeuze") String oefeningkeuze,
                                    @FormParam("schemakeuze") String schemakeuze,
                                    @FormParam("gewichtkeuze") int gewichtkeuze,
                                    @FormParam("sethoeveelheidkeuze") int sethvhkeuze) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            OefeningType oefeningKeuze = OefeningTypeData.getOefeningTypeData().getOefeningType(oefeningkeuze);
            Schema schemaKeuze = currentUser.getSchema(schemakeuze);

            if (oefeningKeuze == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Oefening niet gevonden") {
                        }).build();
            }

            if (schemaKeuze == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Schema niet gevonden") {
                        }).build();
            }

            Oefening nwOefening = new Oefening(gewichtkeuze, sethvhkeuze, oefeningKeuze);
            schemaKeuze.addOefening(nwOefening);

            return Response.ok(nwOefening).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }
}

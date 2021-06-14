package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.AbstractMap;

@Path("/gebruiker")
public class GebruikerResource {

    @GET
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchemas(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            return Response.ok(currentUser.getSessieLijst()).build();
    }
        return Response.status(Response.Status.CONFLICT).entity(
                new AbstractMap.SimpleEntry<String, String>
                        ("error", "Gebruiker niet gevonden") {}).build();
}


//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("a")
//    public Response getOefeningData() {
//
//
//        if (g == null)
//            return Response.status(Response.Status.CONFLICT).entity(
//                    new AbstractMap.SimpleEntry<String, String>
//                            ("result", "Gebruiker niet gevonden") {}).build();
//        return Response.ok(g).build();
//    }

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
        System.out.println(o);
        System.out.println(g);
        return Response.ok(o).build();
    }
}

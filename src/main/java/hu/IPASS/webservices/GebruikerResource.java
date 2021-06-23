package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;

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
    @Path("/settings")
    public Response getSettings(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            return Response.ok(currentUser).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @PUT
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/wachtwoord")
    public Response veranderWachtwoord(@Context SecurityContext sc,
                                       @FormParam("oudwachtwoord") String oudww,
                                       @FormParam("nieuwwachtwoord") String nieuwww) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();

            if (currentUser.checkPassword(oudww)) {
                if (currentUser.setWachtwoord(nieuwww)) {
                    return Response.ok().entity(
                            new AbstractMap.SimpleEntry<>
                                    ("message", "Wachtwoord is veranderd") {
                            }).build();
                } else {
                    return Response.status(Response.Status.CONFLICT).entity(
                            new AbstractMap.SimpleEntry<>
                                    ("error", "Wachtwoorden zijn hetzelfde") {
                            }).build();
                }
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Oude wachtwoord klopt niet") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/maakgebruiker")
    public Response maakGebruikerAan(@FormParam("gebruikernaam") String gebruikernaam,
                                     @FormParam("email") String email,
                                     @FormParam("wachtwoord") String wachtwoord) {

        Gebruiker newGebruiker = new Gebruiker(gebruikernaam, email, wachtwoord, "gebruiker");
        boolean g = Gebruiker.registreerGebruiker(newGebruiker);

        if (g) {
            return Response.ok(newGebruiker).build();
        }
        return Response.status(Response.Status.CONFLICT).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Email bestaat al") {
                }).build();
    }
}

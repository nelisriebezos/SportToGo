package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Schema;
import hu.IPASS.domeinklassen.Sessie;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractMap;

@Path("/sessie")
public class SessieResource {

    @GET
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessies(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            return Response.ok(currentUser.getSessieLijst()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Gebruiker niet geauthoriseerd") {
                    }).build();
        }
    }

    @POST
    @RolesAllowed({"gebruiker", "admin"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response maakSessieAan(@Context SecurityContext sc,
                                  @FormParam("schemakeuze") String schemakeuze,
                                  @FormParam("naamkeuze") String naam,
                                  @FormParam("datumkeuze") String datumkeuze,
                                  @FormParam("begintijdkeuze") String btk,
                                  @FormParam("eindtijdkeuze") String etk) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            Schema schemaKeuze = currentUser.getSchema(schemakeuze);
            LocalDate datum = LocalDate.parse(datumkeuze);
            LocalTime beginTijd = LocalTime.parse(btk);
            LocalTime eindTijd = LocalTime.parse(etk);

            if (schemaKeuze == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Schema is niet gevonden") {
                        }).build();
            }
            Sessie newSessie = new Sessie(naam, datum, beginTijd, eindTijd);
            newSessie.setSchema(schemaKeuze);

            if (currentUser.addSessie(newSessie)) {
                return Response.ok(newSessie).build();
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Sessie bestaat al") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @DELETE
    @RolesAllowed({"admin", "gebruiker"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verwijderSessie(@Context SecurityContext sc,
                                    @FormParam("sessieselect") String sessienaam) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();

            Sessie sessieTeVerwijderen = currentUser.getSessie(sessienaam);

            if (sessieTeVerwijderen == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Sessie is niet gevonden") {
                        }).build();
            }
            if (currentUser.verwijderSessie(sessieTeVerwijderen)) {
                return Response.ok().build();
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Sessie is niet verwijderd") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }
}

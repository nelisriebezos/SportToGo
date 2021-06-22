package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Schema;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.AbstractMap;

@Path("/schema")
public class SchemaResource {

    @GET
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchemas(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            return Response.ok(currentUser.getSchemaLijst()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("error", "Gebruiker niet geauthoriseerd") {
                    }).build();
        }
    }

    @DELETE
    @RolesAllowed({"admin", "gebruiker"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verwijderSessie(@Context SecurityContext sc,
                                    @FormParam("schemaverwijderkeuze") String schemanaam) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();

            Schema SchemaTeVerwijderen = currentUser.getSchema(schemanaam);

            if (SchemaTeVerwijderen == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<String, String>
                                ("error", "Schema niet gevonden") {
                        }).build();
            }
            if (currentUser.verwijderSchema(SchemaTeVerwijderen)) {
                return Response.ok().build();
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("error", "Schema niet verwijderd") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<String, String>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @POST
    @RolesAllowed({"gebruiker", "admin"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response maakSchemaAan(@Context SecurityContext sc,
                                  @FormParam("schemanaam") String schemanaam) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {

            System.out.println(schemanaam);

            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            Schema nwSchema = new Schema(schemanaam);

            System.out.println(nwSchema);

            if (currentUser.addSchema(nwSchema)) {
                return Response.ok(nwSchema).build();
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("error", "Schema is niet toegevoegd") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<String, String>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }
}

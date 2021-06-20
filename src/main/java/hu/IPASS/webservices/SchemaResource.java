package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}

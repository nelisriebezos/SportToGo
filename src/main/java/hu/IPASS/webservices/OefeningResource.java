package hu.IPASS.webservices;

import hu.IPASS.persistence.OefeningTypeData;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/oefening")
public class OefeningResource {

    @GET
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOefeningData() {
        return Response.ok(OefeningTypeData.getOefeningTypeData().getAlleOefeningTypes()).build();
    }
}

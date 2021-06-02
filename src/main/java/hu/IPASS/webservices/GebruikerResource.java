package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.GebruikerData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
                    new AbstractMap.SimpleEntry<String, String>("result", "Gebruiker niet gevonden") {
                    }
            ).build();
        return Response.ok(g).build();
    }
}

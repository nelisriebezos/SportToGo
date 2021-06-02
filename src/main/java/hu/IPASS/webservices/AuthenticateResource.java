package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.GebruikerData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.AbstractMap;


@Path("/authenticate")
public class AuthenticateResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String eM,
                          @FormParam("password") String pW) {

        Gebruiker g = GebruikerData.getGebruikerData().getGebruiker(eM);

        if (g == null)
            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("result", "Gebruiker niet gevonden") {}).build();
        return Response.ok(g).build();
    }
}

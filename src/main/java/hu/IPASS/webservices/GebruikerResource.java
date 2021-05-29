package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.PersistenceManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class GebruikerResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response maakAccountAan(@FormParam("username") String un, @FormParam("emailadres") String eM, @FormParam("password") String pW) {
        Gebruiker geb = new Gebruiker(un, eM, pW);

        if (PersistenceManager.getPM().addGebruikerToList(geb)) {
//            PersistenceManager.getPM().sendUserToAzure(geb);
            System.out.println(geb);
            return Response.ok(geb).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}

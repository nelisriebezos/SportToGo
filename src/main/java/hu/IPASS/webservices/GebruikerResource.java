package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.PersistenceManager;
import hu.IPASS.utils.MakeUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class GebruikerResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String eM,
                          @FormParam("password") String pW) {

//        PersistenceManager.getPM().loadUsersFromAzure();

//        for (Gebruiker g : PersistenceManager.getPM().getGebruikerList()) {
//            if (g.getEmailAdres().equals(eM)) {
//
//                System.out.println(g);
//
//                PersistenceManager.getPM().setCurrentUser(g);
//                return Response.ok().build();
//            }
//        }

        PersistenceManager pm = PersistenceManager.getPM();
        PersistenceManager.getPM().setCurrentUser(MakeUser.makeUserData());

        if (pm.getCurrentUser().getEmailAdres().equals(eM)) {
            System.out.println(pm.getCurrentUser());
            return Response.ok(pm.getCurrentUser()).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}

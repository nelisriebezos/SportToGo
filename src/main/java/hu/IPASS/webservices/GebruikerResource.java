package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.PersistenceManager;
import hu.IPASS.utils.MakeUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@Path("/accounts")
public class GebruikerResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String eM,
                          @FormParam("password") String pW) throws URISyntaxException {

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
        Gebruiker g = pm.getUser(eM, pW);

        if (g != null) {
            HashMap userMap = new HashMap();
            userMap.put("id", g.getId());
            userMap.put("loggedin", "true");
            return Response.ok(userMap).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}

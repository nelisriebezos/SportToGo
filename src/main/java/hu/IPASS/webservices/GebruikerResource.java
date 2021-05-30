package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.PersistenceManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class GebruikerResource {

//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response maakAccountAan(@FormParam("username") String uN,
//                                   @FormParam("emailadres") String eM,
//                                   @FormParam("password") String pW) {
//        Gebruiker geb = new Gebruiker(uN, eM, pW);
//
//        PersistenceManager.getPM().loadUsersFromAzure();
//        if (PersistenceManager.getPM().addGebruikerToList(geb)) {
//
////            PersistenceManager.getPM().sendUserToAzure(geb);
//
//            System.out.println(geb);
//
//            return Response.ok().build();
//        } else {
//            return Response.status(Response.Status.CONFLICT).build();
//        }
//    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String eM,
                          @FormParam("password") String pW) {

        PersistenceManager.getPM().loadUsersFromAzure();

        for (Gebruiker g : PersistenceManager.getPM().getGebruikerList()) {
            if (g.getEmailAdres().equals(eM)) {

                System.out.println(g);

                PersistenceManager.getPM().setCurrentUser(g);
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}

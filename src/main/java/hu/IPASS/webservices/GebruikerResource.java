package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
                          @FormParam("password") String pW) {
        Gebruiker g = GebruikerData.getGebruikerData().getGebruiker(eM);
        System.out.println(g);

        if (g != null) {
//            JsonArrayBuilder jab = Json.createArrayBuilder();
//            JsonObjectBuilder job = Json.createObjectBuilder();
//
//            job.add("id", g.getId());
//            job.add("loggedin", "true");
//
//            jab.add(job);
//            JsonArray array = jab.build();

            HashMap userMap = new HashMap();
            userMap.put("loggedin", "true");


            return Response.ok(userMap).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}

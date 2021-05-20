package hu.IPASS.Webservices;

import hu.IPASS.Domeinklassen.Gebruiker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("test")
public class testding {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendTest() {
        Gebruiker g1 = new Gebruiker("testnaam", "testemail", "testwachtwoord");
        List<Gebruiker> gebruikerList = new ArrayList<>();
        gebruikerList.add(g1);
        return Response.ok(gebruikerList).build();
    }
}

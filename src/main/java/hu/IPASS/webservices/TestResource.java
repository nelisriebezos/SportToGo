package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("test")
public class TestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest() {
        List<Gebruiker> gebruikerList = new ArrayList<>();
        gebruikerList.add(new Gebruiker("testnaam", "testwachtwoor", "testemail"));
        return Response.ok(gebruikerList).build();
    }
}

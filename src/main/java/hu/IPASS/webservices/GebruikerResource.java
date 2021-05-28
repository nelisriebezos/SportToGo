package hu.IPASS.webservices;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.StringReader;

public class GebruikerResource {

    @POST
    @Produces("application/json")
    public String maakAccountAan(String jsonbody) {
        JsonObjectBuilder responseObject = Json.createObjectBuilder();

        try {
            StringReader strReader = new StringReader(jsonbody);
            JsonReader  jsonReader = Json.createReader(strReader);
            JsonObject jsonObject = jsonReader.readObject();

            String naam = jsonObject.getString("username");
            String email = jsonObject.getString("emailadres");
            String wachtwoord = jsonObject.getString("password");

            System.out.println(naam);
            System.out.println(email);
            System.out.println(wachtwoord);

            responseObject.add("message", "gelukt");

        } catch (Exception e) {
            responseObject.add("message", "Error: " + e.getMessage());
        }
        return responseObject.build().toString();
    }
}

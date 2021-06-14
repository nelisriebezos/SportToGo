package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.List;


@Path("/authenticate")
public class AuthenticateResource {
    public static final Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("gebruikernaam") String gebruikernaam,
                                     @FormParam("wachtwoord") String password) {
        try {
            String role = Gebruiker.validateLogin(gebruikernaam, password);
            if (role == null) throw new IllegalArgumentException("No user found");

            String token = createToken(gebruikernaam, role);

            AbstractMap.SimpleEntry<String, String> JWT = new AbstractMap.SimpleEntry<>("JWT", token);
            return Response.ok(JWT).build();

        } catch (JwtException | IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String gebruikernaam, String role) throws JwtException{
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(gebruikernaam)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{email}")
    public Response getUserSchemas(@PathParam("email") String email) {
        Gebruiker g = GebruikerData.getGebruikerData().getGebruiker(email);

        if (g == null)
            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("result", "Gebruiker niet gevonden") {}).build();
        return Response.ok(g.getSchemaLijst()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("alleOefeningTypes")
    public Response getAlleOefeningTypes() {
        List<OefeningType> otd = OefeningTypeData.getOefeningTypeData().getAlleOefeningTypes();

        if (otd == null)
            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<String, String>
                            ("result", "Gebruiker niet gevonden") {}).build();
        return Response.ok(otd).build();

    }
}

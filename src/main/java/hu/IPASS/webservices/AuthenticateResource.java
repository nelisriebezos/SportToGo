package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
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
            return Response.status(Response.Status.UNAUTHORIZED).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Gebruiker niet geauthoriseerd") {
                    }).build();
        }
    }

    private String createToken(String gebruikernaam, String role) throws JwtException{
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 300);

        return Jwts.builder()
                .setSubject(gebruikernaam)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

}

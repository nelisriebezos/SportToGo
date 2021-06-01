//package hu.IPASS.security;
//
//import com.azure.core.annotation.Post;
//import hu.IPASS.domeinklassen.Gebruiker;
//import hu.IPASS.persistence.GebruikerData;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.impl.crypto.MacProvider;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.security.Key;
//import java.util.Calendar;
//
//@Path("/authentication")
//public class AuthenticationResource {
//    GebruikerData gebruikerData = GebruikerData.getGebruikerData();
//    public static final Key key = MacProvider.generateKey();
//
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response authenticateUser(@FormParam("email") String email,
//                                     @FormParam("password") String password) {
//        try {
//            String role = SecurityManager.getSecurityManager().validateLogin(email, password);
//
//            if (role == null) { throw new IllegalArgumentException("No user found or invalid login"); }
//
//            String token = createToken(email, role);
//
//
//        }
//
//
//    }
//
//    private String createToken(String username, String role) {
//        Calendar expiration = Calendar.getInstance();
//        expiration.add(Calendar.MINUTE, 30);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(expiration.getTime())
//                .claim("role", role)
//                .signWith(SignatureAlgorithm.HS512, key)
//                .compact();
//    }
//}

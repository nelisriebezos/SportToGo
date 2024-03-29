package hu.IPASS.security;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.webservices.AuthenticateResource;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestCtx) {

        String scheme = requestCtx.getUriInfo().getRequestUri().getScheme();

        MySecurityContext msc = new MySecurityContext(null, scheme);
        String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring("Bearer".length()).trim();

            try {
                JwtParser parser = Jwts.parser().setSigningKey(AuthenticateResource.key);
                Claims claims = parser.parseClaimsJws(token).getBody();

                String user = claims.getSubject();
                msc = new MySecurityContext(Gebruiker.getUserByName(user), scheme);

            } catch (JwtException | IllegalArgumentException e) {
                System.out.println("Invalid JWT, processing as guest!");
            }
        }

        requestCtx.setSecurityContext(msc);
    }
}


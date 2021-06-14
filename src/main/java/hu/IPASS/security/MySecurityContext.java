package hu.IPASS.security;

import hu.IPASS.domeinklassen.Gebruiker;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private Gebruiker gebruiker;
    private String schema;

    public MySecurityContext(Gebruiker gebruiker, String schema) {
        this.gebruiker = gebruiker;
        this.schema = schema;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.gebruiker;
    }

    @Override
    public boolean isUserInRole(String s) {
        if (gebruiker.getRol() != null) {
            return s.equals(gebruiker.getRol());
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.schema);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}

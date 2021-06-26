package hu.IPASS.webservices;

import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.Oefening;
import hu.IPASS.domeinklassen.Schema;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.AbstractMap;

@Path("/schema")
public class SchemaResource {

    @GET
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchemas(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            return Response.ok(currentUser.getSchemaLijst()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Gebruiker niet geauthoriseerd") {
                    }).build();
        }
    }

    @DELETE
    @RolesAllowed({"admin", "gebruiker"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verwijderSchema(@Context SecurityContext sc,
                                    @FormParam("schemaverwijderkeuze") String schemanaam) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();

            Schema SchemaTeVerwijderen = currentUser.getSchema(schemanaam);

            if (SchemaTeVerwijderen == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Schema is niet gevonden") {
                        }).build();
            }
            if (currentUser.verwijderSchema(SchemaTeVerwijderen)) {
                return Response.ok().entity(
                        new AbstractMap.SimpleEntry<>
                                ("message", "Schema verwijderd")
                ).build();
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Schema is niet verwijderd") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @POST
    @RolesAllowed({"gebruiker", "admin"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response maakSchemaAan(@Context SecurityContext sc,
                                  @FormParam("schemanaam") String schemanaam) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            Schema nwSchema = new Schema(schemanaam);

            if (currentUser.addSchema(nwSchema)) {
                return Response.ok(nwSchema).build();
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Schema bestaat al") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @GET
    @Path("{schemanaam}")
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOefeningenFromSchema(@Context SecurityContext sc,
                                            @PathParam("schemanaam") String schemaKeuze) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            Schema gekozenSchema = currentUser.getSchema(schemaKeuze);

            if (gekozenSchema == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Schema is niet gevonden") {
                        }).build();
            }

            if (gekozenSchema.getOefeningLijst().size() < 1) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "De lijst is leeg") {
                        }).build();
            }

            return Response.ok(gekozenSchema).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @GET
    @Path("{schemakeuze}/{oefeningdata}")
    @RolesAllowed({"gebruiker", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOefeningTeVerwijderen(@Context SecurityContext sc,
                                             @PathParam("oefeningdata") String oefeningdata,
                                             @PathParam("schemakeuze") String schemakeuze) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            Schema gekozenSchema = currentUser.getSchema(schemakeuze);

            if (gekozenSchema == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Schema is niet gevonden") {
                        }).build();
            }

            for (Oefening oef : gekozenSchema.getOefeningLijst()) {
                String oefData = oef.getOefeningType().getNaam() + "," + oef.getGewicht() + "," + oef.getSetHoeveelheid();

                if (oefData.equals(oefeningdata)) {
                    return Response.ok(oef).build();
                }
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Oefening is niet gevonden") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }

    @DELETE
    @Path("{schemakeuze}/{oefeningdata}")
    @RolesAllowed({"admin", "gebruiker"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verwijderOefeningVanSchema(@Context SecurityContext sc,
                                               @PathParam("schemakeuze") String schemakeuze,
                                               @PathParam("oefeningdata") String oefeningdata) {
        if (sc.getUserPrincipal() instanceof Gebruiker) {
            Gebruiker currentUser = (Gebruiker) sc.getUserPrincipal();
            Schema gekozenSchema = currentUser.getSchema(schemakeuze);

            if (gekozenSchema == null) {
                return Response.status(Response.Status.CONFLICT).entity(
                        new AbstractMap.SimpleEntry<>
                                ("error", "Schema is niet gevonden") {
                        }).build();
            }

            for (Oefening oef : gekozenSchema.getOefeningLijst()) {
                String oefData = oef.getOefeningType().getNaam() + "," + oef.getGewicht() + "," + oef.getSetHoeveelheid();

                if (oefData.equals(oefeningdata)) {
                    if (gekozenSchema.verwijderOefening(oef)) {
                        return Response.ok(oef).build();
                    }
                    return Response.status(Response.Status.CONFLICT).entity(
                            new AbstractMap.SimpleEntry<>
                                    ("error", "Oefening is niet verwijderd") {
                            }).build();
                }
            }

            return Response.status(Response.Status.CONFLICT).entity(
                    new AbstractMap.SimpleEntry<>
                            ("error", "Oefening is niet gevonden") {
                    }).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(
                new AbstractMap.SimpleEntry<>
                        ("error", "Gebruiker niet geauthoriseerd") {
                }).build();
    }
}

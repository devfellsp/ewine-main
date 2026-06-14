package br.unitins.topicos1.ewine.resource.desejo;

import br.unitins.topicos1.ewine.service.ListaDesejosService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
@SecurityRequirement(name = "bearerAuth")
public class ListaDesejosResource {

  @Inject JsonWebToken jwt;
  @Inject ListaDesejosService service;

  @GET
  public Response listar() {
    return Response.ok(service.listar(jwt.getSubject())).build();
  }

  @POST
  @Path("/{produtoId}")
  public Response adicionar(@PathParam("produtoId") Long produtoId) {
    return Response.ok(service.adicionar(jwt.getSubject(), produtoId)).build();
  }

  @DELETE
  @Path("/{produtoId}")
  public Response remover(@PathParam("produtoId") Long produtoId) {
    return Response.ok(service.remover(jwt.getSubject(), produtoId)).build();
  }
}

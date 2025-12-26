package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.input.UvaInput;
import br.unitins.topicos1.ewine.service.UvaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/uvas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UvaResource {

  @Inject UvaService service;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response buscarTodos() {
    var uvas = service.buscarTodos();

    return uvas.isEmpty() ? Response.noContent().build() : Response.ok(uvas).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response buscarPorId(@PathParam("id") Long id) {
    var uva = service.buscarPorId(id);

    return Response.ok(uva).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response incluir(UvaInput dto) {
    var uva = service.criar(dto);

    return Response.status(Response.Status.CREATED).entity(uva).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response alterar(@PathParam("id") Long id, UvaInput dto) {
    var uva = service.atualizar(id, dto);

    return Response.ok(uva).build();
  }

  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response apagar(@PathParam("id") Long id) {
    service.deletar(id);

    return Response.noContent().build();
  }
}

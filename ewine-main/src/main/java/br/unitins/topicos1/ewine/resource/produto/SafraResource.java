package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.SafraFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.SafraInput;
import br.unitins.topicos1.ewine.service.SafraService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/safras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SafraResource {

  @Inject SafraService service;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(@BeanParam SafraFilter filtro) {
    var lista = service.filtrar(filtro);

    return lista.isEmpty() ? Response.noContent().build() : Response.ok(lista).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response incluir(SafraInput dto) {
    var safra = service.cadastrar(dto);

    return Response.status(Response.Status.CREATED).entity(safra).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response alterar(@PathParam("id") Long id, SafraInput dto) {
    var safra = service.atualizar(id, dto);

    return Response.ok(safra).build();
  }

  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public void apagar(@PathParam("id") Long id) {
    service.deletar(id);
  }
}

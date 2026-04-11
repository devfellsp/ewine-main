package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.PaisFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.PaisInput;
import br.unitins.topicos1.ewine.service.PaisService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/paises")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaisResource {

  @Inject PaisService service;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(
      @BeanParam PaisFilter filtro,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    var lista = service.filtrar(filtro, page, size);

    return Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response buscarTodos(
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    var paises = service.findAll(page, size);

    return Response.ok(paises).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response buscarPorId(@PathParam("id") Long id) {
    var pais = service.findById(id);

    return Response.ok(pais).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response incluir(@Valid PaisInput dto) {
    var pais = service.create(dto);

    return Response.status(Response.Status.CREATED).entity(pais).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response alterar(@PathParam("id") Long id, @Valid PaisInput dto) {
    var pais = service.update(id, dto);

    return Response.ok(pais).build();
  }

  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response apagar(@PathParam("id") Long id) {
    service.delete(id);

    return Response.noContent().build();
  }
}

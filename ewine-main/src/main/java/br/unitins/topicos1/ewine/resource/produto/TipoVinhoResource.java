package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.TipoVinhoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.TipoVinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.TipoVinhoResponse;
import br.unitins.topicos1.ewine.service.TipoVinhoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/tipos-vinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoVinhoResource {

  @Inject TipoVinhoService tipoVinhoService;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(
      @BeanParam TipoVinhoFilter filtro,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    var lista = tipoVinhoService.filtrar(filtro, page, size);

    return Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response listarTodos(
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    var lista = tipoVinhoService.listarTodos(page, size);

    return Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response buscarPorId(@PathParam("id") Long id) {
    TipoVinhoResponse response = tipoVinhoService.buscarPorId(id);

    return Response.ok(response).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response cadastrar(@Valid TipoVinhoInput input) {
    TipoVinhoResponse response = tipoVinhoService.cadastrar(input);

    return Response.status(Response.Status.CREATED).entity(response).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response atualizar(@PathParam("id") Long id, @Valid TipoVinhoInput input) {
    TipoVinhoResponse response = tipoVinhoService.atualizar(id, input);

    return Response.ok(response).build();
  }

  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response deletar(@PathParam("id") Long id) {
    tipoVinhoService.deletar(id);

    return Response.status(201).build();
  }
}

package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.MarcaFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.MarcaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.MarcaResponse;
import br.unitins.topicos1.ewine.service.MarcaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

  @Inject MarcaService service;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response buscarTodos(
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    var lista = service.buscarTodos(page, size);

    return Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(
      @BeanParam MarcaFilter filtro,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    var lista = service.filtrar(filtro, page, size);

    return Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response buscarPorId(@PathParam("id") Long id) {
    MarcaResponse marca = service.buscarPorId(id);

    return Response.ok(marca).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response cadastrar(@Valid MarcaInput input) {
    MarcaResponse response = service.criar(input);

    return Response.ok(response).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response atualizar(@PathParam("id") Long id, @Valid MarcaInput input) {
    MarcaResponse marca = service.atualizar(id, input);

    return Response.ok(marca).build();
  }

  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response deletar(@PathParam("id") Long id) {
    service.deletar(id);

    return Response.noContent().build();
  }
}

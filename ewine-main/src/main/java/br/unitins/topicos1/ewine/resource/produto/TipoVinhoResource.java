package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.TipoVinhoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.TipoVinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.TipoVinhoResponse;
import br.unitins.topicos1.ewine.service.TipoVinhoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@Path("/tipos-vinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoVinhoResource {

  @Inject TipoVinhoService tipoVinhoService;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(@BeanParam TipoVinhoFilter filtro) {
    var lista = tipoVinhoService.filtrar(filtro);

    return lista.isEmpty() ? Response.noContent().build() : Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response listarTodos() {
    List<TipoVinhoResponse> lista = tipoVinhoService.listarTodos();

    return lista.isEmpty() ? Response.noContent().build() : Response.ok(lista).build();
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
  public Response cadastrar(TipoVinhoInput input) {
    TipoVinhoResponse response = tipoVinhoService.cadastrar(input);

    return Response.status(Response.Status.CREATED).entity(response).build();
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

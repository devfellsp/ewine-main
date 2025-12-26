package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.EstiloFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.EstiloInput;
import br.unitins.topicos1.ewine.service.EstiloService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/estilos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstiloResource {

  @Inject EstiloService service;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(@BeanParam EstiloFilter filtro) {
    var lista = service.filtrar(filtro);

    return lista.isEmpty() ? Response.noContent().build() : Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response buscarTodos() {
    var estilos = service.buscarTodos();

    return estilos.isEmpty() ? Response.noContent().build() : Response.ok(estilos).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response buscarPorId(@PathParam("id") Long id) {
    var estilo = service.buscarPorId(id);

    return Response.ok(estilo).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response incluir(EstiloInput dto) {
    var estilo = service.cadastrar(dto);

    return Response.status(Response.Status.CREATED).entity(estilo).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response alterar(@PathParam("id") Long id, EstiloInput dto) {
    var estilo = service.atualizar(id, dto);

    return Response.ok(estilo).build();
  }

  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public void apagar(@PathParam("id") Long id) {
    service.deletar(id);
  }
}

package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.OcasiaoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.OcasiaoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.OcasiaoResponse;
import br.unitins.topicos1.ewine.service.OcasiaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@Path("/ocasioes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OcasiaoResource {
  @Inject OcasiaoService ocasiaoService;

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/filter")
  public Response filtrar(@BeanParam OcasiaoFilter filtro) {
    var lista = ocasiaoService.filtrar(filtro);

    return lista.isEmpty() ? Response.noContent().build() : Response.ok(lista).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response listar() {
    List<OcasiaoResponse> listas = ocasiaoService.listarTodos();

    return listas.isEmpty() ? Response.noContent().build() : Response.ok(listas).build();
  }

  @PUT
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response atualizar(@PathParam("id") Long id, OcasiaoInput dto) {
    var ocasiao = ocasiaoService.atualizar(id, dto);

    return Response.ok(ocasiao).build();
  }

  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}")
  public Response buscarPorId(@PathParam("id") long id) {
    OcasiaoResponse response = ocasiaoService.buscarPorId(id);

    return Response.ok(response).build();
  }

  @POST
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response cadastrar(OcasiaoInput Input) {
    OcasiaoResponse response = ocasiaoService.criar(Input);
    return Response.status(Response.Status.CREATED).entity(response).build();
  }

  // RETORNO TÁ ERRADO
  @DELETE
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response deletar(long id) {
    ocasiaoService.deletar(id);
    return Response.status(201).build();
  }
}

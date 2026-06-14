package br.unitins.topicos1.ewine.resource.cupom;

import br.unitins.topicos1.ewine.resource.cupom.dto.input.CupomInput;
import br.unitins.topicos1.ewine.service.CupomService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/cupons")
@Produces(MediaType.APPLICATION_JSON)
public class CupomResource {

  @Inject CupomService service;

  @POST
  @RolesAllowed("ADMIN")
  @SecurityRequirement(name = "bearerAuth")
  public Response cadastrar(@Valid CupomInput input) {
    return Response.status(Response.Status.CREATED).entity(service.cadastrar(input)).build();
  }

  @PUT
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  @SecurityRequirement(name = "bearerAuth")
  public Response atualizar(@PathParam("id") Long id, @Valid CupomInput input) {
    return Response.ok(service.atualizar(id, input)).build();
  }

  @PATCH
  @Path("/{id}/ativar")
  @RolesAllowed("ADMIN")
  @SecurityRequirement(name = "bearerAuth")
  public Response alternarAtivo(@PathParam("id") Long id) {
    return Response.ok(service.alternarAtivo(id)).build();
  }

  @GET
  @RolesAllowed("ADMIN")
  @SecurityRequirement(name = "bearerAuth")
  public Response listar() {
    return Response.ok(service.listar()).build();
  }

  @GET
  @Path("/{codigo}")
  @PermitAll
  public Response buscarPorCodigo(@PathParam("codigo") String codigo) {
    return Response.ok(service.buscarPorCodigo(codigo)).build();
  }

  @GET
  @Path("/{codigo}/validar")
  @PermitAll
  public Response validar(
      @PathParam("codigo") String codigo, @QueryParam("valor") @DefaultValue("0") double valor) {
    return Response.ok(service.validar(codigo, valor)).build();
  }
}

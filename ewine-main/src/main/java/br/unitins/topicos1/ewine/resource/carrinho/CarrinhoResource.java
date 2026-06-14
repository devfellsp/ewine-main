package br.unitins.topicos1.ewine.resource.carrinho;

import br.unitins.topicos1.ewine.resource.carrinho.dto.input.FinalizarCarrinhoInput;
import br.unitins.topicos1.ewine.resource.carrinho.dto.input.ItemCarrinhoInput;
import br.unitins.topicos1.ewine.service.CarrinhoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/carrinho")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
@SecurityRequirement(name = "bearerAuth")
public class CarrinhoResource {

  @Inject JsonWebToken jwt;
  @Inject CarrinhoService service;

  @GET
  public Response buscar() {
    return Response.ok(service.buscar(jwt.getSubject())).build();
  }

  @POST
  @Path("/itens")
  public Response adicionarItem(@Valid ItemCarrinhoInput input) {
    return Response.ok(service.adicionarItem(jwt.getSubject(), input)).build();
  }

  @PUT
  @Path("/itens/{produtoId}")
  public Response atualizarItem(@PathParam("produtoId") Long produtoId, @Valid ItemCarrinhoInput input) {
    return Response.ok(service.atualizarItem(jwt.getSubject(), produtoId, input)).build();
  }

  @DELETE
  @Path("/itens/{produtoId}")
  public Response removerItem(@PathParam("produtoId") Long produtoId) {
    return Response.ok(service.removerItem(jwt.getSubject(), produtoId)).build();
  }

  @DELETE
  public Response limpar() {
    service.limpar(jwt.getSubject());
    return Response.noContent().build();
  }

  @POST
  @Path("/finalizar")
  public Response finalizar(@Valid FinalizarCarrinhoInput input) {
    return Response.status(Response.Status.CREATED).entity(service.finalizar(jwt.getSubject(), input)).build();
  }
}

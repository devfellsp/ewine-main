package br.unitins.topicos1.ewine.resource.pedido;

import br.unitins.topicos1.ewine.resource.pedido.dto.input.PedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PedidoResponse;
import br.unitins.topicos1.ewine.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

  @Inject PedidoService service;
  @Inject JsonWebToken jwt;

  @GET
  @RolesAllowed({"ADMIN"}) // Apenas ADMIN pode ver todos os pedidos
  @SecurityRequirement(name = "bearerAuth")
  public Response listarTodos() {
    List<PedidoResponse> pedidos = service.listarTodos();

    return pedidos.isEmpty() ? Response.noContent().build() : Response.ok(pedidos).build();
  }

  @GET
  @Path("/meus-pedidos")
  @RolesAllowed({"CLIENTE"})
  @SecurityRequirement(name = "bearerAuth")
  public Response listarMeusPedidos() {
    List<PedidoResponse> pedidos = service.listarPedidosPorCliente(jwt.getSubject());
    return Response.ok(pedidos).build();
  }

  @POST
  @RolesAllowed({"CLIENTE", "ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response criar(PedidoInput input) {
    PedidoResponse response = service.cadastrar(jwt.getSubject(), input);

    return Response.status(Response.Status.CREATED).entity(response).build();
  }

 /* @PATCH
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}/confirmar-pagamento")
  @Consumes(MediaType.TEXT_PLAIN)
  public Response confirmarPagamento(@PathParam("id") Long id, String transacaoExternaId) {
    PedidoResponse response = service.confirmarPagamento(id, transacaoExternaId);

    return Response.ok(response).build();
  }*/

  @PATCH
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}/recusar-pagamento")
  public Response recusarPagamento(@PathParam("id") Long id) {
    PedidoResponse response = service.recusarPagamento(id);

    return Response.ok(response).build();
  }

  @PATCH
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}/enviar")
  public Response enviar(@PathParam("id") Long id) {

    PedidoResponse response = service.enviar(id);
    return Response.ok(response).build();
  }

  @PATCH
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}/confirmar-entrega")
  public Response confirmarEntrega(@PathParam("id") Long id) {
    PedidoResponse response = service.confirmarEntrega(id);

    return Response.ok(response).build();
  }

  @PATCH
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/{id}/cancelar")
  public Response cancelar(@PathParam("id") Long id) {
    PedidoResponse response = service.cancelar(id);

    return Response.ok(response).build();
  }
}

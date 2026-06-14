package br.unitins.topicos1.ewine.resource.admin;

import br.unitins.topicos1.ewine.resource.admin.dto.AdminAtualizarStatusPedidoInput;
import br.unitins.topicos1.ewine.service.admin.AdminService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
@SecurityRequirement(name = "bearerAuth")
public class AdminResource {

  @Inject AdminService service;

  @GET
  @Path("/dashboard")
  public Response dashboard() {
    return Response.ok(service.dashboard()).build();
  }

  @GET
  @Path("/pedidos")
  public Response listarPedidos(
      @QueryParam("busca") String busca,
      @QueryParam("status") String status,
      @QueryParam("dataInicio") String dataInicio,
      @QueryParam("dataFim") String dataFim,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    return Response.ok(service.listarPedidos(busca, status, dataInicio, dataFim, page, size)).build();
  }

  @GET
  @Path("/pedidos/{id}")
  public Response buscarPedido(@PathParam("id") Long id) {
    return Response.ok(service.buscarPedido(id)).build();
  }

  @PATCH
  @Path("/pedidos/{id}/status")
  public Response atualizarStatusPedido(
      @PathParam("id") Long id, @Valid AdminAtualizarStatusPedidoInput input) {
    return Response.ok(service.atualizarStatusPedido(id, input)).build();
  }

  @PATCH
  @Path("/pedidos/{id}/cancelar")
  public Response cancelarPedido(@PathParam("id") Long id) {
    return Response.ok(service.cancelarPedido(id)).build();
  }

  @GET
  @Path("/vendas")
  public Response listarVendas(
      @QueryParam("dataInicio") String dataInicio,
      @QueryParam("dataFim") String dataFim,
      @QueryParam("statusPagamento") String statusPagamento,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    return Response.ok(service.listarVendas(dataInicio, dataFim, statusPagamento, page, size)).build();
  }

  @GET
  @Path("/vendas/resumo")
  public Response resumoVendas() {
    return Response.ok(service.resumoVendas()).build();
  }

  @GET
  @Path("/clientes")
  public Response listarClientes(
      @QueryParam("busca") String busca,
      @QueryParam("status") String status,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("size") @DefaultValue("10") int size) {
    return Response.ok(service.listarClientes(busca, status, page, size)).build();
  }

  @GET
  @Path("/clientes/{id}")
  public Response buscarCliente(@PathParam("id") Long id) {
    return Response.ok(service.buscarCliente(id)).build();
  }
}

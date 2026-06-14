package br.unitins.topicos1.ewine.service.admin;

import br.unitins.topicos1.ewine.resource.admin.dto.AdminAtualizarStatusPedidoInput;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminClienteDetalheResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminClienteListaResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminDashboardResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminPagedResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminPedidoDetalheResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminPedidoListaResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminVendaResponse;
import br.unitins.topicos1.ewine.resource.admin.dto.AdminVendasResumoResponse;

public interface AdminService {

  AdminDashboardResponse dashboard();

  AdminPagedResponse<AdminPedidoListaResponse> listarPedidos(
      String busca, String status, String dataInicio, String dataFim, int page, int size);

  AdminPedidoDetalheResponse buscarPedido(Long id);

  AdminPedidoDetalheResponse atualizarStatusPedido(Long id, AdminAtualizarStatusPedidoInput input);

  AdminPedidoDetalheResponse cancelarPedido(Long id);

  AdminPagedResponse<AdminVendaResponse> listarVendas(
      String dataInicio, String dataFim, String statusPagamento, int page, int size);

  AdminVendasResumoResponse resumoVendas();

  AdminPagedResponse<AdminClienteListaResponse> listarClientes(
      String busca, String status, int page, int size);

  AdminClienteDetalheResponse buscarCliente(Long id);
}

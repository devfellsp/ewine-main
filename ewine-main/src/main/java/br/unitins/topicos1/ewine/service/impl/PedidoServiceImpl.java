package br.unitins.topicos1.ewine.service. impl;

import br.unitins.topicos1.ewine. infrastructure.persistence.PedidoRepository;
import br.unitins.topicos1.ewine.model.pedido.Pedido;
import br.unitins.topicos1.ewine.resource.pedido.dto.input.PedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PedidoResponse;
import br.unitins.topicos1.ewine.service.PedidoService;
import br.unitins.topicos1.ewine.service.assembler.PedidoAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject. Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import java.util.List;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    // ⬇️ 1. ADICIONAR O LOGGER
    private static final Logger LOG = Logger.getLogger(PedidoServiceImpl.class);

    @Inject PedidoRepository repository;
    @Inject PedidoAssembler assembler;

    @Override
    public List<PedidoResponse> listarPedidosPorCliente(String login) {
        // ⬇️ 2. LOG DE ENTRADA
        LOG.info("Listando pedidos do cliente: " + login);

        List<PedidoResponse> pedidos = assembler.toResponse(repository.findByUsuarioLogin(login));

        // ⬇️ 3. LOG DE RESULTADO
        LOG.info("Encontrados " + pedidos.size() + " pedido(s) para o cliente: " + login);

        return pedidos;
    }

    @Override
    public List<PedidoResponse> listarTodos() {
        // ⬇️ LOG DE ENTRADA
        LOG.info("Listando todos os pedidos");

        List<Pedido> pedidos = repository. findAllOrderByDataCriacaoDesc();

        if (pedidos == null || pedidos.isEmpty()) {
            LOG.info("Nenhum pedido encontrado");
            return List.of();
        }

        // ⬇️ LOG DE RESULTADO
        LOG.info("Total de pedidos encontrados: " + pedidos.size());

        return assembler.toResponse(pedidos);
    }

    @Override
    @Transactional
    public PedidoResponse cadastrar(String login, PedidoInput input) {
        // ⬇️ LOG DE ENTRADA COM DADOS
        LOG.info("===== INICIANDO CADASTRO DE PEDIDO =====");
        LOG.info("Login: " + login);
        LOG.info("Quantidade de itens: " + input. itens().size());
        LOG.info("Parcelas: " + input.parcelas());

        try {

            // ⬇️ LOG DEBUG (mais detalhado)
            LOG.debug("Montando entidade Pedido.. .");
            Pedido pedido = assembler.toEntity(login, input);

            LOG.debug("Valor total do pedido: R$ " + pedido.getTotal());
            LOG.debug("Status inicial: " + pedido.getStatus());

            // ⬇️ LOG ANTES DE SALVAR
            LOG.info("Persistindo pedido no banco de dados...");
            repository.persistAndFlush(pedido);

            // ⬇️ LOG DE SUCESSO
            LOG.info("✅ Pedido cadastrado com sucesso!");
            LOG.info("ID do pedido: " + pedido.getId());
            LOG.info("Valor total: R$ " + pedido.getTotal());
            LOG.info("=========================================");

            return assembler. toResponse(pedido);

        } catch (Exception e) {
            // ⬇️ LOG DE ERRO COM EXCEÇÃO
            LOG.error("❌ ERRO ao cadastrar pedido para o login: " + login);
            LOG.error("Mensagem do erro: " + e.getMessage());
            LOG.error("Tipo do erro: " + e.getClass().getSimpleName());
            throw e;
        }
    }

    @Transactional
    public PedidoResponse confirmarPagamento(Long pedidoId, String transacaoExternaId) {
        // ⬇️ LOG DE ENTRADA
        LOG.info("Confirmando pagamento do pedido ID: " + pedidoId);
        LOG.info("Transação externa: " + transacaoExternaId);

        try {
            Pedido pedido = repository.findById(pedidoId);

            if (pedido == null) {
                LOG.error("Pedido ID " + pedidoId + " não encontrado");
                throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado");
            }

            pedido.confirmarPagamento(transacaoExternaId);

            // ⬇️ LOG DE SUCESSO
            LOG.info("✅ Pagamento confirmado para o pedido ID: " + pedidoId);
            LOG.info("Novo status:  " + pedido.getStatus());

            return assembler.toResponse(pedido);

        } catch (Exception e) {
            LOG.error("❌ Erro ao confirmar pagamento do pedido ID: " + pedidoId);
            LOG.error("Erro: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public PedidoResponse recusarPagamento(Long pedidoId) {
        LOG.info("Recusando pagamento do pedido ID: " + pedidoId);

        try {
            Pedido pedido = repository.findById(pedidoId);

            if (pedido == null) {
                LOG.error("Pedido ID " + pedidoId + " não encontrado");
                throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado");
            }

            pedido.recusarPagamento();

            LOG.info("❌ Pagamento recusado para o pedido ID: " + pedidoId);

            return assembler.toResponse(pedido);

        } catch (Exception e) {
            LOG.error("Erro ao recusar pagamento do pedido ID: " + pedidoId + " - " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public PedidoResponse enviar(Long pedidoId) {
        LOG.info("Enviando pedido ID: " + pedidoId);

        try {
            Pedido pedido = repository.findById(pedidoId);

            if (pedido == null) {
                LOG.error("Pedido ID " + pedidoId + " não encontrado");
                throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado");
            }

            pedido.enviar();

            LOG.info("📦 Pedido ID " + pedidoId + " marcado como ENVIADO");

            return assembler.toResponse(pedido);

        } catch (Exception e) {
            LOG.error("Erro ao enviar pedido ID: " + pedidoId + " - " + e. getMessage());
            throw e;
        }
    }

    @Transactional
    public PedidoResponse confirmarEntrega(Long pedidoId) {
        LOG.info("Confirmando entrega do pedido ID: " + pedidoId);

        try {
            Pedido pedido = repository.findById(pedidoId);

            if (pedido == null) {
                LOG.error("Pedido ID " + pedidoId + " não encontrado");
                throw new jakarta.ws.rs.NotFoundException("Pedido não encontrado");
            }

            pedido. confirmarEntrega();

            LOG.info("✅ Pedido ID " + pedidoId + " marcado como ENTREGUE");

            return assembler.toResponse(pedido);

        } catch (Exception e) {
            LOG.error("Erro ao confirmar entrega do pedido ID: " + pedidoId + " - " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public PedidoResponse cancelar(Long pedidoId) {
        LOG.info("Cancelando pedido ID: " + pedidoId);

        try {
            Pedido pedido = repository.findById(pedidoId);

            if (pedido == null) {
                LOG.error("Pedido ID " + pedidoId + " não encontrado");
                throw new jakarta.ws. rs.NotFoundException("Pedido não encontrado");
            }

            pedido.cancelar();

            LOG.info("🚫 Pedido ID " + pedidoId + " CANCELADO");

            return assembler.toResponse(pedido);

        } catch (Exception e) {
            LOG.error("Erro ao cancelar pedido ID: " + pedidoId + " - " + e. getMessage());
            throw e;
        }
    }
}
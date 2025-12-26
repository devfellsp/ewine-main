package br.unitins.topicos1.ewine.service. impl;

import br.unitins.topicos1.ewine. infrastructure.persistence.PedidoRepository;
import br. unitins.topicos1.ewine.model.pedido.Pedido;
import br.unitins.topicos1.ewine.resource.pagamento.dto.input.ProcessarPagamentoCartaoInput;
import br.unitins.topicos1.ewine. resource.pagamento.dto.response.*;
import br.unitins.topicos1.ewine.service.PagamentoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java. time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    private static final Logger LOG = Logger. getLogger(PagamentoServiceImpl.class);

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public ProcessarPagamentoResponse processarPagamentoPix(Long pedidoId) {
        LOG.info("🟢 Processando pagamento PIX para pedido ID: " + pedidoId);

        Pedido pedido = buscarPedido(pedidoId);
        validarFormaPagamento(pedido, "PIX");

        // Gerar código PIX fictício
        String codigoPix = gerarCodigoPix(pedido. getTotal());
        String qrCodeBase64 = gerarQRCodeBase64(codigoPix);
        String validade = LocalDateTime.now().plusMinutes(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Confirmar pagamento
        String transacaoId = "PIX-" + UUID.randomUUID().toString().substring(0, 8);
        pedido.confirmarPagamento(transacaoId);

        LOG.info("✅ Pagamento PIX processado com sucesso!  Transação:  " + transacaoId);

        return new ProcessarPagamentoResponse(
                pedido.getId(),
                pedido. getStatus(),
                pedido. getPagamento().getStatus(),
                transacaoId,
                new DadosPixResponse(codigoPix, qrCodeBase64, validade),
                null,
                null
        );
    }

    @Override
    @Transactional
    public ProcessarPagamentoResponse processarPagamentoBoleto(Long pedidoId) {
        LOG.info("🟡 Processando pagamento BOLETO para pedido ID: " + pedidoId);

        Pedido pedido = buscarPedido(pedidoId);
        validarFormaPagamento(pedido, "BOLETO");

        // Gerar boleto fictício
        String codigoBarras = gerarCodigoBarras(pedido.getTotal());
        String linhaDigitavel = formatarLinhaDigitavel(codigoBarras);
        String dataVencimento = LocalDateTime. now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);

        // Confirmar pagamento
        String transacaoId = "BOL-" + UUID.randomUUID().toString().substring(0, 8);
        pedido.confirmarPagamento(transacaoId);

        LOG.info("✅ Pagamento BOLETO processado com sucesso!  Transação: " + transacaoId);

        return new ProcessarPagamentoResponse(
                pedido.getId(),
                pedido.getStatus(),
                pedido.getPagamento().getStatus(),
                transacaoId,
                null,
                new DadosBoletoResponse(codigoBarras, linhaDigitavel, dataVencimento),
                null
        );
    }

    @Override
    @Transactional
    public ProcessarPagamentoResponse processarPagamentoCartao(Long pedidoId, ProcessarPagamentoCartaoInput input) {
        LOG.info("🔵 Processando pagamento CARTÃO DE CRÉDITO para pedido ID: " + pedidoId);

        Pedido pedido = buscarPedido(pedidoId);
        validarFormaPagamento(pedido, "CARTÃO");

        // Validar dados do cartão (simulação)
        validarCartao(input);

        // Mascarar número do cartão
        String numeroMascarado = "**** **** **** " + input.numeroCartao().substring(12);

        // Confirmar pagamento
        String transacaoId = "CC-" + UUID.randomUUID().toString().substring(0, 8);
        pedido.confirmarPagamento(transacaoId);

        LOG.info("✅ Pagamento CARTÃO processado com sucesso! Transação: " + transacaoId);

        return new ProcessarPagamentoResponse(
                pedido.getId(),
                pedido. getStatus(),
                pedido. getPagamento().getStatus(),
                transacaoId,
                null,
                null,
                new DadosCartaoResponse(numeroMascarado, input.nomeTitular())
        );
    }

    // ========== MÉTODOS AUXILIARES ==========

    private Pedido buscarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }
        return pedido;
    }

    private void validarFormaPagamento(Pedido pedido, String tipoEsperado) {
        String formaPagamento = pedido. getPagamento().getFormaPagamento().getNome().toUpperCase();

        if (!formaPagamento.contains(tipoEsperado)) {
            throw new IllegalArgumentException(
                    "Forma de pagamento do pedido é " + formaPagamento +
                            ", mas tentou processar como " + tipoEsperado
            );
        }
    }

    private void validarCartao(ProcessarPagamentoCartaoInput input) {
        // Validação de Luhn (algoritmo do cartão) - simplificada
        if (!input.numeroCartao().matches("\\d{16}")) {
            throw new IllegalArgumentException("Número de cartão inválido");
        }

        // Validar validade
        String[] validade = input.validade().split("/");
        int mes = Integer.parseInt(validade[0]);
        int ano = Integer.parseInt(validade[1]);

        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido");
        }

        int anoAtual = LocalDateTime.now().getYear();
        if (ano < anoAtual) {
            throw new IllegalArgumentException("Cartão vencido");
        }
    }

    private String gerarCodigoPix(double valor) {
        return String.format(
                "00020126580014br.gov.bcb.pix0136%s5204000053039865802BR5913Loja Ewine6009SAO PAULO62070503***6304%s",
                UUID.randomUUID().toString(),
                gerarChecksum()
        );
    }
    private String gerarCodigoBarras(double valor) {
        // Gerar 47 dígitos:  formato simplificado
        String valorFormatado = String.format("%010d", (int) (valor * 100));  // 10 dígitos
        String resto = String.format("%037d", System.currentTimeMillis() % 10000000000000L);  // 37 dígitos

        return resto. substring(0, 37) + valorFormatado;  // Total: 47 dígitos
    }

    private String formatarLinhaDigitavel(String codigoBarras) {
        // Garantir 47 caracteres
        if (codigoBarras.length() < 47) {
            codigoBarras = String.format("%-47s", codigoBarras).replace(' ', '0');
        }

        // Formato simplificado:   XXXXX.XXXXX XXXXX. XXXXXX XXXXX. XXXXXX X XXXXXXXXXXXXXX
        return codigoBarras.substring(0, 5) + "." +
                codigoBarras. substring(5, 10) + " " +
                codigoBarras.substring(10, 15) + "." +
                codigoBarras.substring(15, 21) + " " +
                codigoBarras.substring(21, 26) + "." +
                codigoBarras.substring(26, 32) + " " +
                codigoBarras.substring(32, 33) + " " +
                codigoBarras.substring(33, 47);
    }
    private String gerarQRCodeBase64(String codigoPix) {
        // Simulação de QR Code em Base64
        return "iVBORw0KGgoAAAANSUhEUgAAAAUA" + UUID.randomUUID().toString().replace("-", "");
    }

    private String gerarChecksum() {
        return String.format("%04X", new Random().nextInt(65536));
    }
}
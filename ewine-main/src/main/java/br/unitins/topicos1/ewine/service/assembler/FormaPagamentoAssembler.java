package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.FormaPagamentoRepository;
import br.unitins.topicos1.ewine.model.pedido.pagamento.FormaPagamento;
import br.unitins.topicos1.ewine.resource.pagamento.dto.input.FormaPagamentoInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.text.Normalizer;

@ApplicationScoped
public class FormaPagamentoAssembler {

  @Inject FormaPagamentoRepository repository;

  public FormaPagamento toEntity(FormaPagamentoInput input) {
    if (input == null || input.tipo() == null || input.tipo().isBlank()) {
      throw new IllegalArgumentException("Tipo de forma de pagamento e obrigatorio");
    }

    FormaPagamento formaPagamento = repository.findByNome(normalizarTipo(input.tipo()));

    if (formaPagamento == null) {
      throw new jakarta.ws.rs.NotFoundException(
          "Forma de pagamento '" + input.tipo() + "' nao encontrada");
    }

    if (!formaPagamento.isAtivo()) {
      throw new IllegalArgumentException("Forma de pagamento '" + input.tipo() + "' esta inativa");
    }

    return formaPagamento;
  }

  private String normalizarTipo(String tipo) {
    String normalizado =
        Normalizer.normalize(tipo, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")
            .trim()
            .toUpperCase();

    if (normalizado.contains("CARTAO")) {
      return "Cartao de Credito";
    }
    if (normalizado.contains("BOLETO")) {
      return "Boleto Bancario";
    }
    if (normalizado.contains("PIX")) {
      return "PIX";
    }

    return tipo;
  }
}

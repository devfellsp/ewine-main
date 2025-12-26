package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.FormaPagamentoRepository;
import br.unitins.topicos1.ewine.model.pedido.pagamento.FormaPagamento;
import br.unitins.topicos1.ewine.resource.pagamento.dto.input.FormaPagamentoInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FormaPagamentoAssembler {

    @Inject FormaPagamentoRepository repository;

    public FormaPagamento toEntity(FormaPagamentoInput input) {
        if (input == null || input.tipo() == null || input.tipo().isBlank()) {
            throw new IllegalArgumentException("Tipo de forma de pagamento é obrigatório");
        }

        // Buscar forma de pagamento por nome (case-insensitive)
        FormaPagamento formaPagamento = repository.findByNome(input.tipo());

        if (formaPagamento == null) {
            throw new jakarta.ws.rs. NotFoundException(
                    "Forma de pagamento '" + input.tipo() + "' não encontrada"
            );
        }

        if (! formaPagamento.isAtivo()) {
            throw new IllegalArgumentException("Forma de pagamento '" + input.tipo() + "' está inativa");
        }

        return formaPagamento;
    }
}
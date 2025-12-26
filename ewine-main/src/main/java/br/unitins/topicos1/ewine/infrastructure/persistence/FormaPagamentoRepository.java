package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.pedido.pagamento.FormaPagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FormaPagamentoRepository implements PanacheRepository<FormaPagamento> {

    public  FormaPagamento findByNome(String nome) {
        return find("UPPER(nome) = UPPER(?1)", nome.trim()).firstResult();
    }
}

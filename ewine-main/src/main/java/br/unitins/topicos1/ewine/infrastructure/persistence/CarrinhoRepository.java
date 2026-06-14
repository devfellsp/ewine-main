package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.carrinho.Carrinho;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarrinhoRepository implements PanacheRepository<Carrinho> {

  public Carrinho findByCliente(Cliente cliente) {
    return find("cliente", cliente).firstResult();
  }
}

package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.pedido.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

  public List<Pedido> findByUsuarioLogin(String login) {
    return find(
            "SELECT p FROM Pedido p "
                + "JOIN FETCH p.cliente c "
                + "JOIN FETCH c.usuario u "
                + "WHERE u.login = ?1 "
                + "ORDER BY p.dataCriacao DESC",
            login)
        .list();
  }
    public List<Pedido> findAllOrderByDataCriacaoDesc() {
        return find("ORDER BY dataCriacao DESC").list();
    }
}

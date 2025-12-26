package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Cliente findbyNome(String nome) {
        return find("nome = ?1", nome).firstResult();
    }

    public Cliente findByUsuarioLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login é obrigatório");
        }
        return find("select c from Cliente c join c.usuario u where u.login = :login",
                Parameters.with("login", login.toLowerCase().trim()))
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado para o login: " + login));
    }

    public boolean clienteExists(String cpf, Long usuarioId) {

        return count("cpf = ?1 OR usuario. id = ?2", cpf, usuarioId) > 0;
    }

    public boolean emailExists(String email) {
        return !find("email = ?1  ",email).list().isEmpty();
    }
    public boolean cpfExists(String cpf) {
        return !find("cpf = ?1  ",cpf).list().isEmpty();
    }
}

package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.CidadeRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.UsuarioRepository;
import br.unitins.topicos1.ewine.model.location.Cidade;
import br.unitins.topicos1.ewine.model.usuario.Usuario;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.model.usuario.cliente.Endereco;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.ClienteInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.EnderecoInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response.ClienteResponse;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response.EnderecoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClienteAssembler {

  @Inject UsuarioRepository usuarioRepositor;
  @Inject UsuarioAssembler usuarioAssembler;
  @Inject CidadeRepository cidadeRepository;

  public Cliente toEntity(String login, ClienteInput input) {
    Usuario usuario = usuarioRepositor.findByLogin(login);

    return new Cliente(input.cpf(), input.email(), usuario);
  }

  public ClienteResponse toResponse(Cliente cliente) {
    return new ClienteResponse(
        cliente.getCpf(),
        cliente.getEmail(),
        usuarioAssembler.toResponse(cliente.getUsuario()),
        toResponseEndereco(cliente.getEnderecos()));
  }

  public Endereco toEntity(EnderecoInput input) {
    Endereco endereco = new Endereco();
    endereco.setCEP(input.cep());
    endereco.setLogradouro(input.logradouro());
    endereco.setNumero(input.numero());
    endereco.setComplemento(input.complemento());


    if (input.cidade() != null && input.cidade().id() != null) {
      Cidade cidade = cidadeRepository.findById(input.cidade().id());
      if (cidade == null) {
        throw new NotFoundException("Cidade com ID " + input.cidade().id() + " não encontrada");
      }
      endereco.setCidade(cidade);
    }

    return endereco;
  }

  public List<EnderecoResponse> toResponseEndereco(List<Endereco> enderecos) {
    if (enderecos == null || enderecos.isEmpty()) {
      return new ArrayList<>();
    }

    List<EnderecoResponse> response = new ArrayList<>();

    for (Endereco endereco : enderecos) {

      String nomeCidade = endereco.getCidade() != null ? endereco.getCidade().getNome() : null;

      response.add(
          new EnderecoResponse(
              endereco.getId(),
              endereco.getCEP(),
              endereco.getNumero(),
              endereco.getComplemento(),
              endereco.getLogradouro(),
              nomeCidade));
    }
    return response;
  }
}

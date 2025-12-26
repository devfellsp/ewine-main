package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.ClienteRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.UsuarioRepository;
import br.unitins.topicos1.ewine.model.usuario.Perfil;
import br.unitins.topicos1.ewine.model.usuario.Usuario;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.model.usuario.cliente.Endereco;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.command.AtualizarEmailCommand;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.EnderecoInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response.ClienteResponse;
import br.unitins.topicos1.ewine.service.ClienteService;
import br.unitins.topicos1.ewine.service.assembler.ClienteAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.ClienteCadastroInput;
import br.unitins.topicos1.ewine.service.HashService;
import org.jboss.logging.Logger;
import java.util.List;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

  // ⬇️ ADICIONAR O LOGGER
  private static final Logger LOG = Logger.getLogger(ClienteServiceImpl.class);

  @Inject ClienteRepository repository;
  @Inject HashService hashService;
  @Inject UsuarioRepository usuarioRepository;
  @Inject ClienteAssembler assembler;

  @Override
  @Transactional
  public ClienteResponse cadastrar(ClienteCadastroInput input) {
    LOG.info("===== CADASTRANDO NOVO CLIENTE =====");
    LOG.info("Nome: " + input.nome());
    LOG.info("Login: " + input.login());
    LOG.info("CPF: " + input.cpf());
    LOG.info("Email: " + input.email());

    try {
      LOG.debug("Validando login único...");
      validarLoginUnico(input.login());

      LOG.debug("Validando CPF único...");
      cpfUnico(input.cpf());

      LOG.debug("Validando email único...");
      emailUnico(input.email());

      LOG.debug("Gerando hash da senha...");
      String senhaHash = hashService.getHashSenha(input.senha());

      LOG.debug("Criando usuário...");
      Usuario usuario = new Usuario(input.nome(), input.login(), senhaHash, Perfil.CLIENTE);
      usuarioRepository.persist(usuario);

      LOG.debug("Criando cliente...");
      Cliente cliente = new Cliente(input.cpf(), input.email(), usuario);
      repository.persist(cliente);

      LOG.info("✅ Cliente cadastrado com sucesso!");
      LOG.info("ID do usuário: " + usuario.getId());
      LOG.info("ID do cliente: " + cliente.getId());
      LOG.info("====================================");

      return assembler.toResponse(cliente);

    } catch (IllegalArgumentException e) {
      LOG.error("❌ Erro de validação ao cadastrar cliente: " + e.getMessage());
      LOG.error(
          "Login tentado: "
              + input.login()
              + ", CPF: "
              + input.cpf()
              + ", Email: "
              + input.email());
      throw e;
    } catch (Exception e) {
      LOG.error("❌ Erro inesperado ao cadastrar cliente: " + e.getMessage());
      LOG.error("Dados:  Login=" + input.login() + ", CPF=" + input.cpf());
      throw e;
    }
  }

  @Override
  @Transactional
  public ClienteResponse cadastrarEndereco(String login, EnderecoInput input) {
    LOG.info("===== CADASTRANDO ENDEREÇO =====");
    LOG.info("Cliente login: " + login);
    LOG.info("CEP: " + input.cep());
    LOG.info("Logradouro: " + input.logradouro());
    LOG.info("Número: " + input.numero());
    LOG.info("Cidade ID: " + (input.cidade() != null ? input.cidade().id() : "null"));

    try {
      LOG.debug("Montando entidade Endereco...");
      Endereco endereco = assembler.toEntity(input);

      LOG.debug("Buscando cliente por login...");
      Cliente cliente = repository.findByUsuarioLogin(login);

      if (cliente == null) {
        LOG.error("Cliente com login '" + login + "' não encontrado");
        throw new jakarta.ws.rs.NotFoundException("Cliente não encontrado");
      }

      int quantidadeAnterior = cliente.getEnderecos() != null ? cliente.getEnderecos().size() : 0;
      LOG.debug("Cliente possui " + quantidadeAnterior + " endereço(s)");

      cliente.addEndereco(endereco);

      repository.persist(cliente);
      repository.flush();

      LOG.info("✅ Endereço cadastrado com sucesso!");
      LOG.info("Total de endereços do cliente: " + cliente.getEnderecos().size());
      LOG.info("ID do endereço: " + endereco.getId());
      LOG.info("================================");

      return assembler.toResponse(cliente);

    } catch (jakarta.ws.rs.NotFoundException e) {
      LOG.error("❌ " + e.getMessage());
      throw e;
    } catch (Exception e) {
      LOG.error("❌ Erro ao cadastrar endereço para o cliente: " + login);
      LOG.error("Erro: " + e.getMessage());
      throw e;
    }
  }

  @Override
  @Transactional
  public ClienteResponse atualizarEmail(String login, AtualizarEmailCommand emailCommand) {
    LOG.info("===== ATUALIZANDO EMAIL =====");
    LOG.info("Cliente login: " + login);
    LOG.info("Novo email: " + emailCommand.email());

    try {
      LOG.debug("Buscando cliente.. .");
      Cliente cliente = repository.findByUsuarioLogin(login);

      if (cliente == null) {
        LOG.error("Cliente com login '" + login + "' não encontrado");
        throw new jakarta.ws.rs.NotFoundException("Cliente não encontrado");
      }

      String emailAnterior = cliente.getEmail();
      LOG.debug("Email anterior: " + emailAnterior);

      cliente.setEmail(emailCommand.email());

      LOG.info("✅ Email atualizado com sucesso!");
      LOG.info("Email anterior: " + emailAnterior);
      LOG.info("Email atual: " + cliente.getEmail());
      LOG.info("=============================");

      return assembler.toResponse(cliente);

    } catch (Exception e) {
      LOG.error("❌ Erro ao atualizar email do cliente: " + login);
      LOG.error("Erro: " + e.getMessage());
      throw e;
    }
  }

  @Override
  public List<ClienteResponse> listarTodos() {
    LOG.info("Listando todos os clientes");

    List<Cliente> clientes = repository.listAll();

    if (clientes == null || clientes.isEmpty()) {
      LOG.info("Nenhum cliente encontrado");
      return List.of();
    }

    LOG.info("Total de clientes encontrados: " + clientes.size());

    return clientes.stream().map(cliente -> assembler.toResponse(cliente)).toList();
  }

  private void cpfUnico(String cpf) {
    if (repository.cpfExists(cpf)) {
      LOG.warn("⚠️ Tentativa de cadastro com CPF duplicado: " + cpf);
      throw new IllegalArgumentException("cpf existente");
    }
    LOG.debug("CPF " + cpf + " está disponível");
  }

  private void emailUnico(String email) {
    if (repository.emailExists(email)) {
      LOG.warn("⚠️ Tentativa de cadastro com email duplicado: " + email);
      throw new IllegalArgumentException("email existente");
    }
    LOG.debug("Email " + email + " está disponível");
  }

  private void validarLoginUnico(String login) {
    if (usuarioRepository.loginExists(login)) {
      LOG.warn("⚠️ Tentativa de cadastro com login duplicado: " + login);
      throw new IllegalArgumentException("Login já existe");
    }
    LOG.debug("Login " + login + " está disponível");
  }
}

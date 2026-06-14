package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.CupomRepository;
import br.unitins.topicos1.ewine.model.cupom.Cupom;
import br.unitins.topicos1.ewine.resource.cupom.dto.input.CupomInput;
import br.unitins.topicos1.ewine.resource.cupom.dto.response.CupomResponse;
import br.unitins.topicos1.ewine.resource.cupom.dto.response.CupomValidacaoResponse;
import br.unitins.topicos1.ewine.service.CupomService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class CupomServiceImpl implements CupomService {

  @Inject CupomRepository repository;

  @Override
  @Transactional
  public CupomResponse cadastrar(CupomInput input) {
    if (repository.existsByCodigo(input.codigo())) {
      throw new IllegalArgumentException("Cupom ja cadastrado");
    }

    Cupom cupom =
        new Cupom(
            input.codigo(),
            input.percentualDesconto(),
            input.dataValidade(),
            input.quantidadeMaximaUsos());
    repository.persist(cupom);
    return toResponse(cupom);
  }

  @Override
  @Transactional
  public CupomResponse atualizar(Long id, CupomInput input) {
    Cupom cupom = findById(id);
    cupom.atualizar(
        input.codigo(),
        input.percentualDesconto(),
        input.dataValidade(),
        input.quantidadeMaximaUsos());
    return toResponse(cupom);
  }

  @Override
  @Transactional
  public CupomResponse alternarAtivo(Long id) {
    Cupom cupom = findById(id);
    if (cupom.isAtivo()) {
      cupom.desativar();
    } else {
      cupom.ativar();
    }
    return toResponse(cupom);
  }

  @Override
  public List<CupomResponse> listar() {
    return repository.listAll().stream().map(this::toResponse).toList();
  }

  @Override
  public CupomResponse buscarPorCodigo(String codigo) {
    return toResponse(repository.findByCodigo(codigo));
  }

  @Override
  public CupomValidacaoResponse validar(String codigo, double valor) {
    Cupom cupom = repository.findByCodigo(codigo);
    double desconto = cupom.calcularDesconto(valor);
    return new CupomValidacaoResponse(
        cupom.getCodigo(), cupom.isValido(), cupom.getPercentualDesconto(), desconto, valor - desconto);
  }

  private Cupom findById(Long id) {
    return repository
        .findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Cupom nao encontrado"));
  }

  private CupomResponse toResponse(Cupom cupom) {
    return new CupomResponse(
        cupom.getId(),
        cupom.getCodigo(),
        cupom.getPercentualDesconto(),
        cupom.getDataValidade(),
        cupom.getQuantidadeMaximaUsos(),
        cupom.getQuantidadeUsada(),
        cupom.isAtivo(),
        cupom.isValido());
  }
}

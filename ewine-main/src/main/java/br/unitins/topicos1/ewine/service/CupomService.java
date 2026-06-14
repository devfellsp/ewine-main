package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.cupom.dto.input.CupomInput;
import br.unitins.topicos1.ewine.resource.cupom.dto.response.CupomResponse;
import br.unitins.topicos1.ewine.resource.cupom.dto.response.CupomValidacaoResponse;
import java.util.List;

public interface CupomService {
  CupomResponse cadastrar(CupomInput input);
  CupomResponse atualizar(Long id, CupomInput input);
  CupomResponse alternarAtivo(Long id);
  List<CupomResponse> listar();
  CupomResponse buscarPorCodigo(String codigo);
  CupomValidacaoResponse validar(String codigo, double valor);
}

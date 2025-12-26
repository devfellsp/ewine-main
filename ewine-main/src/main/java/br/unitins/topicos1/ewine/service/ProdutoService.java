package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.input.VinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.ProdutoResponse;
import br.unitins.topicos1.ewine.resource.produto.dto.response.VinhoResponse;
import java.util.List;

public interface ProdutoService {

  ProdutoResponse alterarEstoque(Long id, Integer quantidade);

  ProdutoResponse atualizarPreco(Long id, Double novoPreco);

  ProdutoResponse alternarAtivo(Long id);

  VinhoResponse cadastrar(VinhoInput dto);

  VinhoResponse atualizar(Long id, VinhoInput dto);

  Object buscarPorId(Long id);

  List<ProdutoResponse> buscarTodos();

  List<ProdutoResponse> buscarPorNome(String nome);
}

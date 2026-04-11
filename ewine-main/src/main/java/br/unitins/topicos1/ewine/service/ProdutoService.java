package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.input.VinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.ProdutoResponse;
import br.unitins.topicos1.ewine.resource.produto.dto.response.VinhoResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.response.PagedResponse;
import java.util.List;

public interface ProdutoService {

  ProdutoResponse alterarEstoque(Long id, Integer quantidade);

  ProdutoResponse atualizarPreco(Long id, Double novoPreco);

  ProdutoResponse alternarAtivo(Long id);

  VinhoResponse cadastrar(VinhoInput dto);

  VinhoResponse atualizar(Long id, VinhoInput dto);

  void deletar(Long id);

  Object buscarPorId(Long id);

  List<ProdutoResponse> buscarTodos();

  PagedResponse<ProdutoResponse> buscarTodos(int page, int size);

  List<ProdutoResponse> buscarPorNome(String nome);

  PagedResponse<ProdutoResponse> buscarPorNome(String nome, int page, int size);
}

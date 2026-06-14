package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.storage.dto.ImagemUploadResponse;
import java.io.IOException;
import java.nio.file.Path;

public interface ProdutoImagemService {
  ImagemUploadResponse salvarImagem(Long produtoId, Path arquivo, String nomeArquivo, String contentType)
      throws IOException, InterruptedException;
}

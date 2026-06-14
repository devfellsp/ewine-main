package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.resource.storage.dto.ImagemUploadResponse;
import br.unitins.topicos1.ewine.service.ProdutoImagemService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ProdutoImagemServiceImpl implements ProdutoImagemService {

  @Inject ProdutoRepository produtoRepository;

  @ConfigProperty(name = "ewine.storage.seaweed.filer-url", defaultValue = "http://localhost:8888")
  String filerUrl;

  @ConfigProperty(name = "ewine.storage.seaweed.bucket", defaultValue = "ewine-produtos")
  String bucket;

  @ConfigProperty(name = "ewine.storage.local-dir", defaultValue = "uploads/produtos")
  String localDir;

  @ConfigProperty(name = "ewine.storage.local-base-url", defaultValue = "http://localhost:8080/uploads/produtos")
  String localBaseUrl;

  @Override
  @Transactional
  public ImagemUploadResponse salvarImagem(
      Long produtoId, Path arquivo, String nomeArquivo, String contentType)
      throws IOException, InterruptedException {
    Produto produto = produtoRepository.findById(produtoId);

    String extensao = extrairExtensao(nomeArquivo);
    String arquivoFinal = UUID.randomUUID() + extensao;
    String nomeObjeto = "produtos/" + produtoId + "/" + arquivoFinal;
    String url = normalizarUrl(filerUrl) + "/" + bucket + "/" + nomeObjeto;

    try {
      enviarParaSeaweed(url, arquivo, contentType);
    } catch (ConnectException e) {
      url = salvarLocalmente(produtoId, arquivo, arquivoFinal);
    }

    produto.setImagem(url);
    return new ImagemUploadResponse(produto.getId(), produto.getImagem());
  }

  private void enviarParaSeaweed(String url, Path arquivo, String contentType)
      throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder(URI.create(url))
            .PUT(HttpRequest.BodyPublishers.ofByteArray(Files.readAllBytes(arquivo)))
            .header("Content-Type", contentType == null ? "application/octet-stream" : contentType)
            .build();

    HttpResponse<String> response =
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() < 200 || response.statusCode() >= 300) {
      throw new IOException("Falha no upload para SeaweedFS: HTTP " + response.statusCode());
    }
  }

  private String extrairExtensao(String nomeArquivo) {
    if (nomeArquivo == null || !nomeArquivo.contains(".")) {
      return "";
    }
    String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf(".")).toLowerCase(Locale.ROOT);
    return extensao.matches("\\.[a-z0-9]{1,8}") ? extensao : "";
  }

  private String normalizarUrl(String url) {
    return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
  }

  private String salvarLocalmente(Long produtoId, Path arquivo, String arquivoFinal) throws IOException {
    Path pastaProduto = Paths.get(localDir, String.valueOf(produtoId));
    Files.createDirectories(pastaProduto);

    Path destino = pastaProduto.resolve(arquivoFinal);
    Files.copy(arquivo, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

    return normalizarUrl(localBaseUrl) + "/" + produtoId + "/" + arquivoFinal;
  }
}

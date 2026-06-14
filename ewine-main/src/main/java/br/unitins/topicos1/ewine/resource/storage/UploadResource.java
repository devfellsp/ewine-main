package br.unitins.topicos1.ewine.resource.storage;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/uploads/produtos")
public class UploadResource {

  @ConfigProperty(name = "ewine.storage.local-dir", defaultValue = "uploads/produtos")
  String localDir;

  @GET
  @Path("/{produtoId}/{nomeArquivo}")
  @PermitAll
  @Produces({"image/png", "image/jpeg", "image/webp", "image/gif", "application/octet-stream"})
  public Response buscarImagem(
      @PathParam("produtoId") Long produtoId, @PathParam("nomeArquivo") String nomeArquivo)
      throws IOException {
    java.nio.file.Path arquivo = Paths.get(localDir, String.valueOf(produtoId), nomeArquivo);

    if (!Files.exists(arquivo) || !Files.isRegularFile(arquivo)) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    String contentType = Files.probeContentType(arquivo);
    return Response.ok(arquivo.toFile())
        .type(contentType == null ? "application/octet-stream" : contentType)
        .build();
  }
}

package br.unitins.topicos1.ewine.resource.produto;

import br.unitins.topicos1.ewine.resource.produto.dto.input.VinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.ProdutoResponse;
import br.unitins.topicos1.ewine.resource.produto.dto.response.VinhoResponse;
import br.unitins.topicos1.ewine.service.ProdutoService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

  @Inject ProdutoService produtoService;

  @PATCH
  @Path("/{id}/estoque")
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response atualizarEstoque(@PathParam("id") Long id, Integer quantidade) {
    ProdutoResponse response = produtoService.alterarEstoque(id, quantidade);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/{id}/preco")
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response atualizarEstoque(@PathParam("id") Long id, Double valor) {
    ProdutoResponse response = produtoService.atualizarPreco(id, valor);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/{id}/ativar")
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response alternarAtivar(@PathParam("id") Long id) {
    ProdutoResponse response = produtoService.alternarAtivo(id);

    return Response.ok(response).build();
  }

  @GET
  @Path("/{id}")
  @PermitAll
  @SecurityRequirement(name = "bearerAuth")
  public Response findById(@PathParam("id") Long id) {
    Object produto = produtoService.buscarPorId(id);

    return Response.ok(produto).build();
  }

  @GET
  @PermitAll
  @SecurityRequirement(name = "bearerAuth")
  public Response findAll() {
    List<ProdutoResponse> produtos = produtoService.buscarTodos();

    return produtos.isEmpty() ? Response.noContent().build() : Response.ok(produtos).build();
  }

  @GET
  @PermitAll
  @SecurityRequirement(name = "bearerAuth")
  @Path("/search")
  public Response findByNome(@QueryParam("nome") String nome) {
    List<ProdutoResponse> resultados = produtoService.buscarPorNome(nome);

    return resultados.isEmpty() ? Response.noContent().build() : Response.ok(resultados).build();
  }

  @POST
  @Path("/vinhos")
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response create(@Valid VinhoInput dto) {
    VinhoResponse criado = produtoService.cadastrar(dto);

    return Response.status(Response.Status.CREATED).entity(criado).build();
  }

  @PUT
  @Path("/{id}/vinhos")
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response atualizar(@PathParam("id") Long id, @Valid VinhoInput dto) {
    VinhoResponse response = produtoService.atualizar(id, dto);

    return Response.status(Response.Status.OK).entity(response).build();
  }
}

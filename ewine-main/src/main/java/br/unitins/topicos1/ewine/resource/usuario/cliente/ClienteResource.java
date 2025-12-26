package br.unitins.topicos1.ewine.resource.usuario.cliente;



import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.command.AtualizarEmailCommand;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.ClienteCadastroInput; // NOVO
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.EnderecoInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response.ClienteResponse;
import br.unitins.topicos1.ewine.service.ClienteService;
import jakarta.annotation.security.PermitAll; // NOVO
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation; // NOVO
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

  @Inject JsonWebToken jwt;
  @Inject ClienteService clienteService;


    @POST
    @PermitAll
    @Operation(
            summary = "Cadastrar novo cliente",
            description = "Cria um novo usuário e cliente simultaneamente. Não requer autenticação."
    )
    public Response cadastrar(@Valid ClienteCadastroInput input) {
        ClienteResponse response = clienteService.cadastrar(input);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
  @PATCH
  @Path("/enderecos")
  @RolesAllowed("CLIENTE")
  @SecurityRequirement(name = "bearerAuth")
  public Response cadastrarEndereco(EnderecoInput input) {
    ClienteResponse response = clienteService.cadastrarEndereco(jwt.getSubject(), input);

    return Response.status(Response.Status.CREATED).entity(response).build();
  }
    @PATCH
    @RolesAllowed("CLIENTE")
    @SecurityRequirement(name = "bearerAuth")
    public Response atualizarEmail(
            @Context SecurityContext securityContext,
            @Valid AtualizarEmailCommand command) {

        String login = securityContext. getUserPrincipal().getName();

        ClienteResponse response = clienteService.atualizarEmail(login, command);

        return Response.ok(response).build();
    }
    @GET
    @RolesAllowed({"ADMIN"}) // Apenas Admin pode listar todos
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar todos os clientes",
            description = "Retorna lista de todos os clientes com seus usuários associados")
    public Response buscarTodos() {
        List<ClienteResponse> clientes = clienteService.listarTodos();

        return clientes.isEmpty()
                ? Response.noContent().build()
                : Response.ok(clientes).build();
    }
}


package br.unitins.topicos1.ewine.resource.usuario;

import br.unitins.topicos1.ewine.resource.usuario.dto.command.AtualizarSenhaCommand;
import br.unitins.topicos1.ewine.resource.usuario.dto.command.AtualizarUsuarioCommand;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.UsuarioInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;
import br.unitins.topicos1.ewine.service.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

  @Inject UsuarioService usuarioService;
  @Inject JsonWebToken jwt;
/*
  @POST
  @PermitAll
  public Response cadastrar(UsuarioInput input) {
    var usuario = usuarioService.cadastrar(input);

    return Response.status(Response.Status.CREATED).entity(usuario).build();
  }
*/
  @GET
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response buscarTodos() {
    List<UsuarioResponse> lista = usuarioService.buscarTodos();

    return lista.isEmpty() ? Response.noContent().build() : Response.ok(lista).build();
  }

  @GET
  @Path("/{id}")
  @RolesAllowed({"ADMIN", "CLIENTE"})
  @SecurityRequirement(name = "bearerAuth")
  public Response buscarPorId(@PathParam("id") Long id) {
    var usuario = usuarioService.buscarPorId(id);

    return Response.ok(usuario).build();
  }

  @PUT
  @RolesAllowed({"ADMIN", "CLIENTE"})
  @SecurityRequirement(name = "bearerAuth")
  public Response atualizar(AtualizarUsuarioCommand command) {
    usuarioService.atualizar(jwt.getSubject(), command);

    return Response.ok().build();
  }

  @PATCH
  @RolesAllowed({"ADMIN", "CLIENTE"})
  @SecurityRequirement(name = "bearerAuth")
  @Path("/senha")
  public Response atualizarSenha(AtualizarSenhaCommand command) {
    usuarioService.atualizarSenha(jwt.getSubject(), command);

    return Response.ok().build();
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed({"ADMIN"})
  @SecurityRequirement(name = "bearerAuth")
  public Response deletar(@PathParam("id") Long id) {
    usuarioService.deletar(id);

    return Response.noContent().build();
  }
}

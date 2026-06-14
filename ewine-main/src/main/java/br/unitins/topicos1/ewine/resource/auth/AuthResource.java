package br.unitins.topicos1.ewine.resource.auth;

import br.unitins.topicos1.ewine.resource.auth.dto.input.LoginInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.RedefinirSenhaInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.SolicitarRecuperacaoSenhaInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;
import br.unitins.topicos1.ewine.service.AuthService;
import br.unitins.topicos1.ewine.service.SenhaRecuperacaoService;
import br.unitins.topicos1.ewine.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  @Inject UsuarioService usuarioService;

  @Inject AuthService authService;

  @Inject SenhaRecuperacaoService senhaRecuperacaoService;

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Response login(LoginInput input) {
    UsuarioResponse usuario = usuarioService.buscarPorLoginESenha(input);

    if (usuario == null) return Response.status(Response.Status.UNAUTHORIZED).build();

    String token = authService.generateToken(usuario.login(), usuario.perfil());

    return Response.ok().header("Authorization", "Bearer " + token).entity(token).build();
  }

  @POST
  @Path("/logout")
  public Response logout() {
    return Response.noContent().build();
  }

  @POST
  @Path("/recuperar-senha")
  public Response solicitarRecuperacaoSenha(@Valid SolicitarRecuperacaoSenhaInput input) {
    return Response.ok(senhaRecuperacaoService.solicitar(input)).build();
  }

  @POST
  @Path("/redefinir-senha")
  public Response redefinirSenha(@Valid RedefinirSenhaInput input) {
    senhaRecuperacaoService.redefinir(input);
    return Response.noContent().build();
  }
}

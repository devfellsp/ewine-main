package br.unitins.topicos1.ewine.resource.auth;

import br.unitins.topicos1.ewine.resource.auth.dto.input.LoginInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;
import br.unitins.topicos1.ewine.service.AuthService;
import br.unitins.topicos1.ewine.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  @Inject UsuarioService usuarioService;
  @Inject AuthService authService;

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Response login(LoginInput input) {
    UsuarioResponse usuario = usuarioService.buscarPorLoginESenha(input);

    if (usuario == null) return Response.noContent().build();

    String token = authService.generateToken(usuario.login(), usuario.perfil());

    // ✅ Token enviado como HttpOnly Cookie — JavaScript não consegue acessar
    NewCookie cookie = new NewCookie.Builder("token")
            .value(token)
            .httpOnly(true)
            .secure(false)        // muda para true em produção (HTTPS)
            .sameSite(NewCookie.SameSite.STRICT)
            .path("/")
            .build();

    // ✅ Retorna também o perfil no body para o frontend saber a role
    return Response.ok(usuario.perfil()).cookie(cookie).build();
  }

  @POST
  @Path("/logout")
  public Response logout() {
    // ✅ Apaga o cookie no navegador
    NewCookie cookie = new NewCookie.Builder("token")
            .value("")
            .httpOnly(true)
            .secure(false)        // muda para true em produção
            .sameSite(NewCookie.SameSite.STRICT)
            .path("/")
            .maxAge(0)
            .build();

    return Response.ok().cookie(cookie).build();
  }
}
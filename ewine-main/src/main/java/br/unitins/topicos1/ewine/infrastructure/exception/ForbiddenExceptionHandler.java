package br.unitins.topicos1.ewine.infrastructure.exception;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class ForbiddenExceptionHandler implements ExceptionMapper<ForbiddenException> {

  @Override
  public Response toResponse(ForbiddenException exception) {
    ErrorResponse error =
        ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(403)
            .error("Forbidden")
            .message("Você não tem permissão para acessar este recurso")
            .build();

    return Response.status(Response.Status.FORBIDDEN).entity(error).build();
  }
}

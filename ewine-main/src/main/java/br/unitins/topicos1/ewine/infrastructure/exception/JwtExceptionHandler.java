package br.unitins.topicos1.ewine.infrastructure.exception;

import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class JwtExceptionHandler implements ExceptionMapper<ParseException> {

    @Override
    public Response toResponse(ParseException exception) {
        String message = "Token inválido";

        if (exception.getMessage() != null && exception.getMessage().contains("exp")) {
            message = "Token expirado";
        }

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(401)
                .error("Unauthorized")
                .message(message)
                .build();

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(error)
                .build();
    }
}
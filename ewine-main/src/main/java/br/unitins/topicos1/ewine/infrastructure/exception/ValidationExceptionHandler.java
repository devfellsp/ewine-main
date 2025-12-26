package br.unitins.topicos1.ewine.infrastructure.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    Map<String, String> errors = new HashMap<>();

    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      String propertyPath = violation.getPropertyPath().toString();
      String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
      errors.put(fieldName, violation.getMessage());
    }

    ErrorResponse error =
        ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(400)
            .error("Validation Failed")
            .message("Erro de validação nos campos")
            .fieldErrors(errors)
            .build();

    return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
  }
}

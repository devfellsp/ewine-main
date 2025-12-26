package br.unitins.topicos1.ewine. resource. pagamento;

import br.unitins.topicos1.ewine.resource.pagamento.dto. input.ProcessarPagamentoCartaoInput;
import br. unitins.topicos1.ewine.resource.pagamento.dto.response.ProcessarPagamentoResponse;
import br.unitins.topicos1.ewine.service.PagamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs. core.Response;
import org.eclipse.microprofile.openapi. annotations.security.SecurityRequirement;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)

public class PagamentoResource {

    @Inject
    PagamentoService service;

    @POST
    @Path("/{pedidoId}/pix")
    @RolesAllowed({"CLIENTE", "ADMIN"})
    @SecurityRequirement(name = "bearerAuth")
    public Response processarPagamentoPix(@PathParam("pedidoId") Long pedidoId) {
        ProcessarPagamentoResponse response = service.processarPagamentoPix(pedidoId);
        return Response.ok(response).build();
    }

    @POST
    @Path("/{pedidoId}/boleto")
    @RolesAllowed({"CLIENTE", "ADMIN"})
    @SecurityRequirement(name = "bearerAuth")
    public Response processarPagamentoBoleto(@PathParam("pedidoId") Long pedidoId) {
        ProcessarPagamentoResponse response = service.processarPagamentoBoleto(pedidoId);
        return Response.ok(response).build();
    }

    @POST
    @Path("/{pedidoId}/cartao")
    @RolesAllowed({"CLIENTE", "ADMIN"})
    @SecurityRequirement(name = "bearerAuth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processarPagamentoCartao(
            @PathParam("pedidoId") Long pedidoId,
            @Valid ProcessarPagamentoCartaoInput input
    ) {
        ProcessarPagamentoResponse response = service.processarPagamentoCartao(pedidoId, input);
        return Response.ok(response).build();
    }
}
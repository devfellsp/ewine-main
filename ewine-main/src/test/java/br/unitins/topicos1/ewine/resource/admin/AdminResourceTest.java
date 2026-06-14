package br.unitins.topicos1.ewine.resource.admin;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminResourceTest {

  private String getToken(String login, String senha) {
    return given()
        .contentType(ContentType.JSON)
        .body(
            """
            {
              "login": "%s",
              "senha": "%s"
            }
            """
                .formatted(login, senha))
        .when()
        .post("/auth")
        .then()
        .statusCode(200)
        .extract()
        .asString();
  }

  private Integer criarPedidoPix(String token) {
    return given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body(
            """
            {
              "parcelas": 1,
              "formaPagamento": {
                "tipo": "PIX"
              },
              "endereco": {
                "id": 1
              },
              "itens": [
                {
                  "produtoId": 1,
                  "quantidade": 1
                }
              ]
            }
            """)
        .when()
        .post("/pedidos")
        .then()
        .statusCode(201)
        .extract()
        .path("id");
  }

  private void pagarPix(String token, Integer pedidoId) {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .post("/pagamentos/" + pedidoId + "/pix")
        .then()
        .statusCode(200);
  }

  @Test
  @Order(1)
  @DisplayName("Cliente nao acessa endpoints admin")
  public void clienteNaoAcessaAdmin() {
    String tokenCliente = getToken("joao", "123");

    given()
        .header("Authorization", "Bearer " + tokenCliente)
        .when()
        .get("/admin/dashboard")
        .then()
        .statusCode(403);
  }

  @Test
  @Order(2)
  @DisplayName("Admin lista pedidos")
  public void adminListaPedidos() {
    String tokenAdmin = getToken("admin", "123");

    given()
        .header("Authorization", "Bearer " + tokenAdmin)
        .when()
        .get("/admin/pedidos")
        .then()
        .statusCode(200)
        .body("itens", notNullValue())
        .body("total", notNullValue())
        .body("pagina", equalTo(0))
        .body("tamanho", equalTo(10));
  }

  @Test
  @Order(3)
  @DisplayName("Admin consulta detalhe do pedido")
  public void adminConsultaDetalhePedido() {
    String tokenCliente = getToken("joao", "123");
    String tokenAdmin = getToken("admin", "123");
    Integer pedidoId = criarPedidoPix(tokenCliente);

    given()
        .header("Authorization", "Bearer " + tokenAdmin)
        .when()
        .get("/admin/pedidos/" + pedidoId)
        .then()
        .statusCode(200)
        .body("id", equalTo(pedidoId))
        .body("cliente", notNullValue())
        .body("itens", notNullValue())
        .body("linhaTempo", notNullValue());
  }

  @Test
  @Order(4)
  @DisplayName("Admin altera status do pedido")
  public void adminAlteraStatusPedido() {
    String tokenCliente = getToken("joao", "123");
    String tokenAdmin = getToken("admin", "123");
    Integer pedidoId = criarPedidoPix(tokenCliente);
    pagarPix(tokenCliente, pedidoId);

    given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + tokenAdmin)
        .body(
            """
            {
              "status": "EM_SEPARACAO"
            }
            """)
        .when()
        .patch("/admin/pedidos/" + pedidoId + "/status")
        .then()
        .statusCode(200)
        .body("status", equalTo("EM_SEPARACAO"));
  }

  @Test
  @Order(5)
  @DisplayName("Admin cancela pedido")
  public void adminCancelaPedido() {
    String tokenCliente = getToken("joao", "123");
    String tokenAdmin = getToken("admin", "123");
    Integer pedidoId = criarPedidoPix(tokenCliente);

    given()
        .header("Authorization", "Bearer " + tokenAdmin)
        .when()
        .patch("/admin/pedidos/" + pedidoId + "/cancelar")
        .then()
        .statusCode(200)
        .body("status", equalTo("CANCELADO"));
  }

  @Test
  @Order(6)
  @DisplayName("Admin consulta resumo de vendas")
  public void adminConsultaResumoVendas() {
    String tokenAdmin = getToken("admin", "123");

    given()
        .header("Authorization", "Bearer " + tokenAdmin)
        .when()
        .get("/admin/vendas/resumo")
        .then()
        .statusCode(200)
        .body("receitaTotal", notNullValue())
        .body("quantidadePedidosPagos", notNullValue());
  }

  @Test
  @Order(7)
  @DisplayName("Admin lista clientes")
  public void adminListaClientes() {
    String tokenAdmin = getToken("admin", "123");

    given()
        .header("Authorization", "Bearer " + tokenAdmin)
        .when()
        .get("/admin/clientes")
        .then()
        .statusCode(200)
        .body("itens", notNullValue())
        .body("total", notNullValue());
  }
}

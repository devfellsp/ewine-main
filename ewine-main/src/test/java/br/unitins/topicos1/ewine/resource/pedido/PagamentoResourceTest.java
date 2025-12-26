package br.unitins.topicos1.ewine.resource.pagamento;

import io.quarkus.test. junit.QuarkusTest;
import io.restassured.http.ContentType;
import org. junit.jupiter.api.*;

import static io.restassured.RestAssured. given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer. OrderAnnotation.class)
public class PagamentoResourceTest {

    // ========================================
    // HELPER:  Gerar Token e Criar Pedido
    // ========================================

    private String getToken(String login, String senha) {
        return given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "login": "%s",
                            "senha":  "%s"
                        }
                        """.formatted(login, senha))
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }

    private Integer criarPedido(String token, String tipoPagamento, int parcelas) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": %d,
                            "formaPagamento": {
                                "tipo": "%s"
                            },
                            "endereco":  { "id": 1 },
                            "itens": [
                                {
                                    "produtoId": 1,
                                    "quantidade": 1
                                }
                            ]
                        }
                        """.formatted(parcelas, tipoPagamento))
                .when()
                .post("/pedidos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    // ========================================
    // TESTES - PROCESSAR PAGAMENTO PIX
    // ========================================

    @Test
    @Order(1)
    @DisplayName("✅ Processar pagamento PIX com sucesso")
    public void testProcessarPagamentoPix() {
        String token = getToken("joao", "123");
        Integer pedidoId = criarPedido(token, "PIX", 1);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/pagamentos/" + pedidoId + "/pix")
                .then()
                .statusCode(200)
                .body("pedidoId", equalTo(pedidoId))
                .body("statusPedido", equalTo("PAGO"))
                .body("statusPagamento", equalTo("CONFIRMADO"))
                .body("transacaoId", notNullValue())
                .body("transacaoId", startsWith("PIX-"))
                .body("pix", notNullValue())
                .body("pix. codigoPix", notNullValue())
                .body("pix. qrCodeBase64", notNullValue())
                .body("pix.validade", notNullValue())
                .body("boleto", nullValue())
                .body("cartao", nullValue());

        System.out.println("✅ Pagamento PIX processado com sucesso!");
    }

    @Test
    @Order(2)
    @DisplayName("❌ Erro 404 - PIX com pedido inexistente")
    public void testPixPedidoInexistente() {
        String token = getToken("joao", "123");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/pagamentos/999999/pix")
                .then()
                                                                                                                                        .statusCode(404);

        System.out.println("✅ Teste PIX pedido inexistente passou!");
    }

    // ========================================
    // TESTES - PROCESSAR PAGAMENTO BOLETO
    // ========================================

    @Test
    @Order(3)
    @DisplayName("✅ Processar pagamento Boleto com sucesso")
    public void testProcessarPagamentoBoleto() {
        String token = getToken("joao", "123");
        Integer pedidoId = criarPedido(token, "Boleto Bancário", 1);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/pagamentos/" + pedidoId + "/boleto")
                .then()
                .statusCode(200)
                .body("pedidoId", equalTo(pedidoId))
                .body("statusPedido", equalTo("PAGO"))
                .body("statusPagamento", equalTo("CONFIRMADO"))
                .body("transacaoId", notNullValue())
                .body("transacaoId", startsWith("BOL-"))
                .body("boleto", notNullValue())
                .body("boleto.codigoBarras", notNullValue())
                .body("boleto.linhaDigitavel", notNullValue())
                .body("boleto.dataVencimento", notNullValue())
                .body("pix", nullValue())
                .body("cartao", nullValue());

        System.out.println("✅ Pagamento Boleto processado com sucesso!");
    }

    @Test
    @Order(4)
    @DisplayName("❌ Erro 404 - Boleto com pedido inexistente")
    public void testBoletoPedidoInexistente() {
        String token = getToken("joao", "123");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/pagamentos/999999/boleto")
                .then()
                .statusCode(404);

        System.out.println("✅ Teste Boleto pedido inexistente passou!");
    }

    // ========================================
    // TESTES - PROCESSAR PAGAMENTO CARTÃO
    // ========================================

    @Test
    @Order(5)
    @DisplayName("✅ Processar pagamento Cartão à vista")
    public void testProcessarPagamentoCartaoAVista() {
        String token = getToken("joao", "123");
        Integer pedidoId = criarPedido(token, "Cartão de Crédito", 1);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "numeroCartao": "1234567890123456",
                            "nomeTitular": "JOAO SILVA",
                            "validade": "12/2028",
                            "cvv":  "123"
                        }
                        """)
                .when()
                .post("/pagamentos/" + pedidoId + "/cartao")
                .then()
                .statusCode(200)
                .body("pedidoId", equalTo(pedidoId))
                .body("statusPedido", equalTo("PAGO"))
                .body("statusPagamento", equalTo("CONFIRMADO"))
                .body("transacaoId", notNullValue())
                .body("transacaoId", startsWith("CC-"))
                .body("cartao", notNullValue())
                .body("cartao.numeroMascarado", equalTo("**** **** **** 3456"))
                .body("cartao.nomeTitular", equalTo("JOAO SILVA"))
                .body("pix", nullValue())
                .body("boleto", nullValue());

        System.out.println("✅ Pagamento Cartão à vista processado!");
    }

    @Test
    @Order(6)
    @DisplayName("✅ Processar pagamento Cartão parcelado")
    public void testProcessarPagamentoCartaoParcelado() {
        String token = getToken("joao", "123");
        Integer pedidoId = criarPedido(token, "Cartão de Crédito", 3);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "numeroCartao": "9876543210987654",
                            "nomeTitular": "MARIA SANTOS",
                            "validade":  "06/2027",
                            "cvv":  "456"
                        }
                        """)
                .when()
                .post("/pagamentos/" + pedidoId + "/cartao")
                .then()
                .statusCode(200)
                .body("statusPedido", equalTo("PAGO"))
                .body("cartao.numeroMascarado", equalTo("**** **** **** 7654"))
                .body("cartao.nomeTitular", equalTo("MARIA SANTOS"));

        System.out.println("✅ Pagamento Cartão parcelado processado!");
    }

    @Test
    @Order(7)
    @DisplayName("❌ Erro 400 - Cartão com número inválido")
    public void testCartaoNumeroInvalido() {
        String token = getToken("joao", "123");
        Integer pedidoId = criarPedido(token, "Cartão de Crédito", 1);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "numeroCartao": "123",
                            "nomeTitular": "TESTE",
                            "validade":  "12/2028",
                            "cvv": "123"
                        }
                        """)
                .when()
                .post("/pagamentos/" + pedidoId + "/cartao")
                .then()
                .statusCode(400);

        System.out.println("✅ Teste cartão inválido passou!");
    }

    @Test
    @Order(8)
    @DisplayName("❌ Erro 400 - Cartão vencido")
    public void testCartaoVencido() {
        String token = getToken("joao", "123");
        Integer pedidoId = criarPedido(token, "Cartão de Crédito", 1);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "numeroCartao": "1234567890123456",
                            "nomeTitular": "TESTE",
                            "validade":  "12/2020",
                            "cvv":  "123"
                        }
                        """)
                .when()
                .post("/pagamentos/" + pedidoId + "/cartao")
                .then()
                .statusCode(400);

        System.out.println("✅ Teste cartão vencido passou!");
    }

    @Test
    @Order(9)
    @DisplayName("❌ Erro 404 - Cartão com pedido inexistente")
    public void testCartaoPedidoInexistente() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "numeroCartao": "1234567890123456",
                            "nomeTitular": "TESTE",
                            "validade": "12/2028",
                            "cvv":  "123"
                        }
                        """)
                .when()
                .post("/pagamentos/999999/cartao")
                .then()
                .statusCode(404);

        System.out.println("✅ Teste cartão pedido inexistente passou!");
    }

    // ========================================
    // TESTES - SEGURANÇA
    // ========================================

    @Test
    @Order(10)
    @DisplayName("🔒 Erro 401 - Processar pagamento sem token")
    public void testProcessarPagamentoSemToken() {
        given()
                .when()
                .post("/pagamentos/1/pix")
                .then()
                .statusCode(401);

        System.out.println("✅ Teste 401 passou!");
    }
}
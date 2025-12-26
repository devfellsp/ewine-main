package br.unitins.topicos1.ewine.resource.pedido;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured. given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer. OrderAnnotation.class)
public class PedidoResourceTest {

    // ========================================
    // HELPER:  Gerar Token
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

    // ========================================
    // TESTES - CRIAR PEDIDOS
    // ========================================

    @Test
    @Order(1)
    @DisplayName("✅ Cliente cria pedido com Cartão de Crédito (parcelado)")
    public void testCriarPedidoCartaoParcelado() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 3,
                            "formaPagamento": {
                                "tipo": "Cartão de Crédito"
                            },
                            "endereco": { "id": 1 },
                            "itens": [
                                {
                                    "produtoId": 1,
                                    "quantidade": 2
                                }
                            ]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("status", equalTo("AGUARDANDO_PAGAMENTO"))
                .body("pagamento.status", equalTo("PENDENTE"))
                .body("pagamento. parcelas", equalTo(3))
                .body("pagamento.formaPagamento.nome", equalTo("Cartão de Crédito"))
                .body("pagamento.valorTotal", notNullValue())
                .body("itens[0].quantidade", equalTo(2));

        System.out.println("✅ Pedido com Cartão parcelado criado!");
    }

    @Test
    @Order(2)
    @DisplayName("✅ Cliente cria pedido com Cartão à vista")
    public void testCriarPedidoCartaoAVista() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType. JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas":  1,
                            "formaPagamento": {
                                "tipo": "Cartão de Crédito"
                            },
                            "endereco":  { "id": 1 },
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
                .body("status", equalTo("AGUARDANDO_PAGAMENTO"))
                .body("pagamento.parcelas", equalTo(1))
                .body("pagamento.formaPagamento.nome", equalTo("Cartão de Crédito"));

        System.out.println("✅ Pedido com Cartão à vista criado!");
    }

    @Test
    @Order(3)
    @DisplayName("✅ Cliente cria pedido com PIX")
    public void testCriarPedidoPix() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 1,
                            "formaPagamento": {
                                "tipo": "PIX"
                            },
                            "endereco":  { "id": 1 },
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
                .body("status", equalTo("AGUARDANDO_PAGAMENTO"))
                .body("pagamento.formaPagamento.nome", equalTo("PIX"))
                .body("pagamento.parcelas", equalTo(1));

        System.out.println("✅ Pedido com PIX criado!");
    }

    @Test
    @Order(4)
    @DisplayName("✅ Cliente cria pedido com Boleto")
    public void testCriarPedidoBoleto() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 1,
                            "formaPagamento": {
                                "tipo":  "Boleto Bancário"
                            },
                            "endereco": { "id": 1 },
                            "itens": [
                                {
                                    "produtoId":  1,
                                    "quantidade": 1
                                }
                            ]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(201)
                .body("status", equalTo("AGUARDANDO_PAGAMENTO"))
                .body("pagamento.formaPagamento.nome", equalTo("Boleto Bancário"));

        System.out.println("✅ Pedido com Boleto criado!");
    }

    @Test
    @Order(5)
    @DisplayName("✅ Cliente cria pedido com múltiplos produtos")
    public void testCriarPedidoMultiplosProdutos() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 2,
                            "formaPagamento": {
                                "tipo": "Cartão de Crédito"
                            },
                            "endereco": { "id": 1 },
                            "itens": [
                                {
                                    "produtoId": 1,
                                    "quantidade": 2
                                },
                                {
                                    "produtoId": 1,
                                    "quantidade":  3
                                }
                            ]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(201)
                .body("status", equalTo("AGUARDANDO_PAGAMENTO"))
                .body("itens. size()", equalTo(2))
                .body("itens[0].quantidade", equalTo(2))
                .body("itens[1]. quantidade", equalTo(3));

        System.out.println("✅ Pedido com múltiplos produtos criado!");
    }

    // ========================================
    // TESTES - VALIDAÇÕES
    // ========================================

    @Test
    @Order(6)
    @DisplayName("❌ Erro 401 - Criar pedido sem token")
    public void testCriarPedidoSemToken() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "parcelas": 1,
                            "formaPagamento": { "tipo": "PIX" },
                            "endereco": { "id": 1 },
                            "itens": [{ "produtoId": 1, "quantidade": 1 }]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(401);

        System.out.println("✅ Teste 401 passou!");
    }

    @Test
    @Order(7)
    @DisplayName("❌ Erro 404 - Produto inexistente")
    public void testProdutoInexistente() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 1,
                            "formaPagamento": { "tipo": "PIX" },
                            "endereco": { "id": 1 },
                            "itens": [{ "produtoId":  999999, "quantidade": 1 }]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(404);

        System.out.println("✅ Teste 404 passou!");
    }

    @Test
    @Order(8)
    @DisplayName("❌ Erro 400 - Parcelamento inválido (15x)")
    public void testParcelamentoInvalido() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 15,
                            "formaPagamento":  { "tipo": "Cartão de Crédito" },
                            "endereco":  { "id": 1 },
                            "itens": [{ "produtoId": 1, "quantidade": 1 }]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(400);

        System.out.println("✅ Teste 400 (15x) passou!");
    }

    @Test
    @Order(9)
    @DisplayName("❌ Erro 400 - PIX não aceita parcelamento")
    public void testPixNaoAceitaParcelamento() {
        String token = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "parcelas": 3,
                            "formaPagamento": { "tipo": "PIX" },
                            "endereco": { "id": 1 },
                            "itens": [{ "produtoId":  1, "quantidade": 1 }]
                        }
                        """)
                .when()
                .post("/pedidos")
                .then()
                .statusCode(400);

        System.out.println("✅ Teste 400 (PIX parcelado) passou!");
    }
}
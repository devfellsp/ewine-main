package br.unitins.topicos1.ewine.resource.pedido;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoResourceTest {

    private String getToken(String login, String senha) {
        return given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "login": "%s",
                            "senha": "%s"
                        }
                        """.formatted(login, senha))
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }

    @Test
    @Order(1)
    @DisplayName("Listar todos os produtos publico")
    public void testListarTodosProdutos() {
        given()
                .when()
                .get("/produtos")
                .then()
                .statusCode(200)
                .body("content", notNullValue())
                .body("content[0].id", notNullValue())
                .body("content[0].nome", notNullValue())
                .body("content[0].preco", notNullValue());
    }

    @Test
    @Order(2)
    @DisplayName("Buscar produto por ID")
    public void testBuscarProdutoPorId() {
        given()
                .when()
                .get("/produtos/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("nome", notNullValue())
                .body("preco", notNullValue())
                .body("quantEstoque", notNullValue());
    }

    @Test
    @Order(3)
    @DisplayName("Erro 404 - Produto inexistente")
    public void testProdutoInexistente() {
        given()
                .when()
                .get("/produtos/999999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    @DisplayName("Buscar produtos por nome")
    public void testBuscarProdutosPorNome() {
        given()
                .queryParam("nome", "Miolo")
                .when()
                .get("/produtos/search")
                .then()
                .statusCode(200)
                .body("content", notNullValue())
                .body("content[0].nome", containsString("Miolo"));
    }

    @Test
    @Order(5)
    @DisplayName("Busca sem resultados retorna lista vazia")
    public void testBuscaSemResultados() {
        given()
                .queryParam("nome", "ProdutoQueNaoExiste12345")
                .when()
                .get("/produtos/search")
                .then()
                .statusCode(200)
                .body("content", hasSize(0));
    }

    @Test
    @Order(6)
    @DisplayName("Admin cria vinho com todos os atributos")
    public void testAdminCriarProduto() {
        String tokenAdmin = getToken("admin", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body("""
                    {
                        "nome": "Vinho Teste Automatizado",
                        "descricao": "Descricao do vinho de teste",
                        "preco": 129.90,
                        "sku": "TEST-AUTO-001",
                        "quantEstoque": 50,
                        "teorAlcoolico": 13.5,
                        "volume": 750,
                        "pais": { "id": 1 },
                        "tipoVinho": { "id": 1 },
                        "marca": { "id": 1 },
                        "safra": { "id": 1 },
                        "estilo": { "id": 1 },
                        "ocasiao": { "id": 1 },
                        "uvas": [
                            { "id": 1 }
                        ]
                    }
                    """)
                .when()
                .post("/produtos/vinhos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("Vinho Teste Automatizado"))
                .body("descricao", equalTo("Descricao do vinho de teste"))
                .body("preco", equalTo(129.90f))
                .body("sku", equalTo("TEST-AUTO-001"))
                .body("quantEstoque", equalTo(50))
                .body("teorAlcoolico", equalTo(13.5f))
                .body("volume", equalTo(750))
                .body("paisDeOrigem.id", equalTo(1))
                .body("tipoVinho.id", equalTo(1))
                .body("marca.id", equalTo(1))
                .body("safra.id", equalTo(1))
                .body("estilo.id", equalTo(1))
                .body("ocasiao.id", equalTo(1))
                .body("uvas", hasSize(1))
                .body("uvas[0].id", equalTo(1));
    }

    @Test
    @Order(7)
    @DisplayName("Erro 400 - Criacao de vinho invalida")
    public void testAdminNaoCriaProdutoComAtributosInvalidos() {
        String tokenAdmin = getToken("admin", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body("""
                    {
                        "nome": "",
                        "descricao": "curta",
                        "preco": 0,
                        "sku": "",
                        "quantEstoque": -1,
                        "teorAlcoolico": null,
                        "volume": 0,
                        "pais": { "id": null },
                        "tipoVinho": { "id": 0 },
                        "marca": { "id": 1 },
                        "safra": { "id": 1 },
                        "uvas": []
                    }
                    """)
                .when()
                .post("/produtos/vinhos")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(8)
    @DisplayName("Erro 403 - Cliente nao pode criar produto")
    public void testClienteNaoPodeCriarProduto() {
        String tokenCliente = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenCliente)
                .body(vinhoValidoJson("TEST-CLIENTE-001"))
                .when()
                .post("/produtos/vinhos")
                .then()
                .statusCode(403);
    }

    @Test
    @Order(9)
    @DisplayName("Erro 401 - Criar produto sem token")
    public void testCriarProdutoSemToken() {
        given()
                .contentType(ContentType.JSON)
                .body(vinhoValidoJson("TEST-SEM-TOKEN-001"))
                .when()
                .post("/produtos/vinhos")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(10)
    @DisplayName("Admin atualiza vinho")
    public void testAdminAtualizarProduto() {
        String tokenAdmin = getToken("admin", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body("""
                    {
                        "nome": "Vinho Atualizado",
                        "descricao": "Descricao atualizada",
                        "preco": 149.90,
                        "sku": "UPD-001",
                        "quantEstoque": 100,
                        "teorAlcoolico": 14.0,
                        "volume": 750,
                        "pais": { "id": 1 },
                        "tipoVinho": { "id": 1 },
                        "marca": { "id": 1 },
                        "safra": { "id": 1 },
                        "estilo": { "id": 1 },
                        "ocasiao": { "id": 1 },
                        "uvas": [{ "id": 1 }]
                    }
                    """)
                .when()
                .put("/produtos/1/vinhos")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Vinho Atualizado"))
                .body("preco", equalTo(149.90f));
    }

    @Test
    @Order(11)
    @DisplayName("Erro 403 - Cliente nao pode atualizar produto")
    public void testClienteNaoPodeAtualizarProduto() {
        String tokenCliente = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenCliente)
                .body(vinhoValidoJson("FAIL-001"))
                .when()
                .put("/produtos/1/vinhos")
                .then()
                .statusCode(403);
    }

    @Test
    @Order(12)
    @DisplayName("Admin deleta produto")
    public void testAdminDeletarProduto() {
        String tokenAdmin = getToken("admin", "123");

        Integer produtoId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body(vinhoValidoJson("DEL-001"))
                .when()
                .post("/produtos/vinhos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .header("Authorization", "Bearer " + tokenAdmin)
                .when()
                .delete("/produtos/" + produtoId)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(13)
    @DisplayName("Erro 403 - Cliente nao pode deletar produto")
    public void testClienteNaoPodeDeletarProduto() {
        String tokenCliente = getToken("joao", "123");

        given()
                .header("Authorization", "Bearer " + tokenCliente)
                .when()
                .delete("/produtos/1")
                .then()
                .statusCode(403);
    }

    private String vinhoValidoJson(String sku) {
        return """
                {
                    "nome": "Vinho Teste",
                    "descricao": "Descricao completa do vinho teste",
                    "preco": 99.90,
                    "sku": "%s",
                    "quantEstoque": 10,
                    "teorAlcoolico": 13.0,
                    "volume": 750,
                    "pais": { "id": 1 },
                    "tipoVinho": { "id": 1 },
                    "marca": { "id": 1 },
                    "safra": { "id": 1 },
                    "uvas": [{ "id": 1 }]
                }
                """.formatted(sku);
    }
}

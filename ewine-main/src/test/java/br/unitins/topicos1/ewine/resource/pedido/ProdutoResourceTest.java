package br.unitins.topicos1.ewine.resource.pedido;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured. given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoResourceTest {

    // ========================================
    // HELPER:  Gerar Token
    // ========================================

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

    // ========================================
    // TESTES - LISTAR PRODUTOS
    // ========================================

    @Test
    @Order(1)
    @DisplayName("✅ Listar todos os produtos (público)")
    public void testListarTodosProdutos() {
        given()
                .when()
                .get("/produtos")
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].nome", notNullValue())
                .body("[0].preco", notNullValue());

        System.out.println("✅ Produtos listados com sucesso!");
    }

    @Test
    @Order(2)
    @DisplayName("✅ Buscar produto por ID")
    public void testBuscarProdutoPorId() {
        given()
                .when()
                .get("/produtos/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("nome", notNullValue())
                .body("preco", notNullValue())
                .body("quantEstoque", notNullValue());  // ← CORRIGIDO

        System.out.println("✅ Produto encontrado por ID!");
    }

    @Test
    @Order(3)
    @DisplayName("❌ Erro 404 - Produto inexistente")
    public void testProdutoInexistente() {
        given()
                .when()
                .get("/produtos/999999")
                .then()
                .statusCode(404);

        System.out.println("✅ Teste 404 produto inexistente passou!");
    }

    // ========================================
    // TESTES - BUSCAR POR NOME
    // ========================================

    @Test
    @Order(4)
    @DisplayName("✅ Buscar produtos por nome")
    public void testBuscarProdutosPorNome() {
        given()
                .queryParam("nome", "Miolo")
                .when()
                .get("/produtos/search")
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("[0].nome", containsString("Miolo"));

        System.out.println("✅ Busca por nome funcionou!");
    }

    @Test
    @Order(5)
    @DisplayName("✅ Busca sem resultados retorna 204")
    public void testBuscaSemResultados() {
        given()
                .queryParam("nome", "ProdutoQueNaoExiste12345")
                .when()
                .get("/produtos/search")
                .then()
                .statusCode(204);  // ← Aceitar 204

        System.out.println("✅ Busca vazia retornou 204!");
    }

    // ========================================
    // TESTES - CRIAR PRODUTO (ADMIN)
    // ========================================

    @Test
    @Order(6)
    @DisplayName("✅ Admin cria vinho com sucesso")
    public void testAdminCriarProduto() {
        String tokenAdmin = getToken("admin", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body("""
                    {
                        "nome": "Vinho Teste Automatizado",
                        "descricao": "Descrição do vinho de teste",
                        "preco":  129.90,
                        "sku": "TEST-AUTO-001",
                        "estoqueQuantidade": 50,
                        "teorAlcoolico": 13.5,
                        "volume": 750,
                        "paisDeOrigem": { "id": 1 },
                        "tipoVinho": { "id": 1 },
                        "marca":  { "id": 1 },
                        "safra": { "id": 1 },
                        "estilo": { "id": 1 },
                        "ocasiao": { "id": 1 },
                        "uvas": [
                            { "id": 1 }
                        ]
                    }
                    """)
                .when()
                .post("/produtos/vinhos")  // ← CAMINHO CORRETO
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("Vinho Teste Automatizado"))
                .body("preco", equalTo(129.90f));

        System.out.println("✅ Vinho criado pelo admin!");
    }

    @Test
    @Order(9)
    @DisplayName("✅ Admin atualiza vinho")
    public void testAdminAtualizarProduto() {
        String tokenAdmin = getToken("admin", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body("""
                    {
                        "nome": "Vinho Atualizado",
                        "descricao": "Descrição atualizada",
                        "preco": 149.90,
                        "sku": "UPD-001",
                        "estoqueQuantidade": 100,
                        "teorAlcoolico": 14.0,
                        "volume": 750,
                        "paisDeOrigem": { "id": 1 },
                        "tipoVinho": { "id": 1 },
                        "marca": { "id": 1 },
                        "safra":  { "id": 1 },
                        "estilo": { "id": 1 },
                        "ocasiao": { "id": 1 },
                        "uvas": [{ "id": 1 }]
                    }
                    """)
                .when()
                .put("/produtos/1/vinhos")  // ← CAMINHO CORRETO
                .then()
                .statusCode(200)
                .body("nome", equalTo("Vinho Atualizado"))
                .body("preco", equalTo(149.90f));

        System.out.println("✅ Vinho atualizado!");
    }
    @Test
    @Order(7)
    @DisplayName("🔒 Erro 403 - Cliente não pode criar produto")
    public void testClienteNaoPodeCriarProduto() {
        String tokenCliente = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenCliente)
                .body("""
                        {
                            "nome": "Vinho Teste",
                            "descricao":  "Teste",
                            "preco": 99.90,
                            "sku":  "TEST-002",
                            "estoqueQuantidade": 10
                        }
                        """)
                .when()
                .post("/produtos")
                .then()
                .statusCode(403);

        System.out. println("✅ Teste 403 cliente não cria produto passou!");
    }

    @Test
    @Order(8)
    @DisplayName("🔒 Erro 401 - Criar produto sem token")
    public void testCriarProdutoSemToken() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "nome": "Vinho Teste",
                            "descricao":  "Teste",
                            "preco": 99.90,
                            "sku": "TEST-003",
                            "estoqueQuantidade": 10
                        }
                        """)
                .when()
                .post("/produtos")
                .then()
                .statusCode(401);

        System.out. println("✅ Teste 401 sem token passou!");
    }

    // ========================================
    // TESTES - ATUALIZAR PRODUTO (ADMIN)
    // ========================================

    @Test
    @Order(10)
    @DisplayName("🔒 Erro 403 - Cliente não pode atualizar produto")
    public void testClienteNaoPodeAtualizarProduto() {
        String tokenCliente = getToken("joao", "123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenCliente)
                .body("""
                        {
                            "nome": "Tentativa de atualização",
                            "descricao": "Teste",
                            "preco":  99.90,
                            "sku": "FAIL-001",
                            "estoqueQuantidade": 10
                        }
                        """)
                .when()
                .put("/produtos/1")
                .then()
                .statusCode(403);

        System.out.println("✅ Teste 403 cliente não atualiza passou!");
    }

    // ========================================
    // TESTES - DELETAR PRODUTO (ADMIN)
    // ========================================

    @Test
    @Order(11)
    @DisplayName("✅ Admin deleta produto (soft delete)")
    public void testAdminDeletarProduto() {
        String tokenAdmin = getToken("admin", "123");

        // Criar produto para deletar
        Integer produtoId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .body("""
                        {
                            "nome": "Vinho Para Deletar",
                            "descricao": "Será deletado",
                            "preco": 79.90,
                            "sku": "DEL-001",
                            "estoqueQuantidade": 5
                        }
                        """)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Deletar
        given()
                .header("Authorization", "Bearer " + tokenAdmin)
                .when()
                .delete("/produtos/" + produtoId)
                .then()
                .statusCode(204);

        System.out.println("✅ Produto deletado!");
    }

    @Test
    @Order(12)
    @DisplayName("🔒 Erro 403 - Cliente não pode deletar produto")
    public void testClienteNaoPodeDeletarProduto() {
        String tokenCliente = getToken("joao", "123");

        given()
                .header("Authorization", "Bearer " + tokenCliente)
                .when()
                .delete("/produtos/1")
                .then()
                .statusCode(403);

        System.out.println("✅ Teste 403 cliente não deleta passou!");
    }
}
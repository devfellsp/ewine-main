# Endpoints Admin

Todos os endpoints abaixo exigem token JWT com perfil `ADMIN`.

Header:

```http
Authorization: Bearer <token>
```

## Dashboard

```http
GET /admin/dashboard
```

Retorna indicadores gerais, pedidos recentes e resumo de vendas.

## Pedidos

```http
GET /admin/pedidos?busca=joao&status=PAGO&dataInicio=2026-06-01&dataFim=2026-06-30&page=0&size=10
```

Filtros opcionais:

- `busca`: cliente, email ou numero do pedido
- `status`: `AGUARDANDO_PAGAMENTO`, `PAGO`, `EM_SEPARACAO`, `SAIU_PARA_ENTREGA`, `ENTREGUE`, `CANCELADO`
- `dataInicio`: formato `yyyy-MM-dd`
- `dataFim`: formato `yyyy-MM-dd`
- `page`
- `size`

Resposta paginada:

```json
{
  "itens": [],
  "total": 0,
  "pagina": 0,
  "tamanho": 10
}
```

```http
GET /admin/pedidos/{id}
```

```http
PATCH /admin/pedidos/{id}/status
Content-Type: application/json
```

```json
{
  "status": "EM_SEPARACAO"
}
```

```http
PATCH /admin/pedidos/{id}/cancelar
```

## Vendas

```http
GET /admin/vendas?dataInicio=2026-06-01&dataFim=2026-06-30&statusPagamento=CONFIRMADO&page=0&size=10
```

Filtros opcionais:

- `dataInicio`: formato `yyyy-MM-dd`
- `dataFim`: formato `yyyy-MM-dd`
- `statusPagamento`: `PENDENTE`, `CONFIRMADO`, `RECUSADO`
- `page`
- `size`

```http
GET /admin/vendas/resumo
```

## Clientes

```http
GET /admin/clientes?busca=joao&status=ATIVO&page=0&size=10
```

Filtros opcionais:

- `busca`: nome, email ou CPF
- `status`: `ATIVO` ou `INATIVO`
- `page`
- `size`

```http
GET /admin/clientes/{id}
```


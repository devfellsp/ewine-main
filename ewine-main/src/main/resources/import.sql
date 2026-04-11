-- =========================
-- PAÍSES
-- =========================
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Brasil', 'BR');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'França', 'FR');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Itália', 'IT');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Espanha', 'ES');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Portugal', 'PT');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Chile', 'CL');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Argentina', 'AR');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Estados Unidos', 'US');

-- =========================
-- ESTADOS
-- =========================
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Rio Grande do Sul', 'RS', 4);
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Santa Catarina', 'SC', 4);
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'São Paulo', 'SP', 3);
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Minas Gerais', 'MG', 3);

-- =========================
-- CIDADES
-- =========================
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Bento Gonçalves', 1);
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Garibaldi', 1);
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Caxias do Sul', 1);
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Videira', 2);
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'São Roque', 3);
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Andradas', 4);

-- =========================
-- MARCAS
-- =========================
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Miolo', 'Brasil', 1989, 'Premium');

INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Casa Valduga', 'Brasil', 1875, 'Premium');

INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Salton', 'Brasil', 1910, 'Standard');

INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Château Margaux', 'França', 1815, 'Grand Cru');

INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Antinori', 'Itália', 1385, 'Premium');

INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Concha y Toro', 'Chile', 1883, 'Standard');

INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao)
VALUES (nextval('marca_id_seq'), 'Catena Zapata', 'Argentina', 1902, 'Premium');

-- =========================
-- ESTILOS
-- =========================
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Seco');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Suave');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Meio Seco');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Doce');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Frisante');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Espumante');

-- =========================
-- SAFRAS
-- =========================
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2020, 'Safra excepcional com clima equilibrado');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2019, 'Ótima safra, inverno rigoroso');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2018, 'Safra clássica, taninos equilibrados');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2017, 'Safra elegante, boa acidez');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2016, 'Safra concentrada, grande potencial');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2015, 'Safra histórica, qualidade superior');

-- =========================
-- OCASIÕES
-- =========================
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Jantar Romântico');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Churrasco');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Confraternização');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Celebração');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Degustação');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Harmonização');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Presente');

-- =========================
-- TIPOS DE VINHO
-- =========================
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Tinto');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Branco');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Rosé');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Espumante');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Fortificado');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Sobremesa');

-- =========================
-- UVAS
-- =========================
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Cabernet Sauvignon');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Merlot');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Tannat');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Malbec');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Chardonnay');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Sauvignon Blanc');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Pinot Noir');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Moscato');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Riesling');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Tempranillo');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Syrah');
INSERT INTO Uva (id, nome) VALUES (nextval('uva_id_seq'), 'Sangiovese');

-- =========================
-- USUÁRIO CLIENTE
-- =========================
INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (
           nextval('usuario_id_seq'),
           'João Silva',
           'joao',
           '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==',
           'CLIENTE',
           true
       );

INSERT INTO Cliente (id, cpf, email, usuario_id)
VALUES (nextval('cliente_id_seq'), '12345678901', 'joao.silva@email.com', currval('usuario_id_seq'));

-- =========================
-- ENDEREÇO
-- =========================
INSERT INTO Endereco (id, cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (
           nextval('endereco_id_seq'),
           '77001-000',
           'Quadra 103 Norte',
           10,
           'Apto 201',
           1,
           currval('cliente_id_seq')
       );

-- =========================
-- USUÁRIO ADMIN
-- =========================
INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (
           nextval('usuario_id_seq'),
           'Administrador',
           'admin',
           '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==',
           'ADMIN',
           true
       );

-- =========================
-- FORMAS DE PAGAMENTO
-- =========================
INSERT INTO forma_pagamento (id, nome, permite_parcelamento, ativo)
VALUES (nextval('forma_pagamento_id_seq'), 'Cartão de Crédito', true, true);

INSERT INTO forma_pagamento (id, nome, permite_parcelamento, ativo)
VALUES (nextval('forma_pagamento_id_seq'), 'PIX', false, true);

INSERT INTO forma_pagamento (id, nome, permite_parcelamento, ativo)
VALUES (nextval('forma_pagamento_id_seq'), 'Boleto Bancário', false, true);

-- =========================
-- PRODUTO (VINHO)
-- =========================
INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-MIO-CS-2020-750',
           'Miolo Reserva Cabernet Sauvignon',
           'Vinho tinto elegante com notas de frutas vermelhas maduras, taninos suaves e final persistente.',
           NULL,
           true,
           89.90,
           50,
           13.5,
           750,
           1,
           1,
           1,
           1,
           1,
           2
       );

-- =========================
-- UVAS DO VINHO
-- =========================
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 1);
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 2);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-VAL-MER-2019-750',
           'Casa Valduga Terroir Merlot',
           'Merlot brasileiro macio, com aromas de ameixa, especiarias e final aveludado.',
           NULL,
           true,
           119.90,
           35,
           13.0,
           750,
           1,
           1,
           2,
           2,
           1,
           1
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 2);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-SAL-TAN-2018-750',
           'Salton Intenso Tannat',
           'Tinto encorpado com taninos marcantes, notas de frutas negras e toque tostado.',
           NULL,
           true,
           74.90,
           42,
           13.8,
           750,
           1,
           1,
           3,
           3,
           3,
           2
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 3);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-MAR-PN-2017-750',
           'Chateau Margaux Pinot Noir',
           'Pinot Noir frances delicado, com notas florais, cereja fresca e acidez elegante.',
           NULL,
           true,
           389.90,
           12,
           12.8,
           750,
           2,
           1,
           4,
           4,
           1,
           5
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 7);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-ANT-SAN-2016-750',
           'Antinori Chianti Sangiovese',
           'Vinho italiano vibrante, com notas de cereja, ervas secas e taninos finos.',
           NULL,
           true,
           159.90,
           28,
           13.5,
           750,
           3,
           1,
           5,
           5,
           1,
           6
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 12);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-CYT-CAB-2020-750',
           'Concha y Toro Cabernet Sauvignon',
           'Cabernet chileno com corpo medio, frutas negras, pimenta e boa persistencia.',
           NULL,
           true,
           69.90,
           60,
           13.2,
           750,
           6,
           1,
           6,
           1,
           1,
           2
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 1);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-CAT-MAL-2019-750',
           'Catena Zapata Malbec',
           'Malbec argentino intenso, com ameixa madura, violeta e final longo.',
           NULL,
           true,
           139.90,
           31,
           14.0,
           750,
           7,
           1,
           7,
           2,
           1,
           4
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 4);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-MIO-CHA-2020-750',
           'Miolo Chardonnay Reserva',
           'Branco fresco e equilibrado, com notas de abacaxi, pera e leve toque amanteigado.',
           NULL,
           true,
           84.90,
           44,
           12.5,
           750,
           1,
           2,
           1,
           1,
           1,
           6
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 5);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-VAL-MOS-2020-750',
           'Casa Valduga Moscatel Espumante',
           'Espumante aromatico, leve e refrescante, com notas de flores brancas e frutas tropicais.',
           NULL,
           true,
           94.90,
           55,
           7.5,
           750,
           1,
           4,
           2,
           1,
           6,
           4
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 8);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-CYT-SBL-2020-750',
           'Concha y Toro Sauvignon Blanc',
           'Branco chileno com acidez viva, notas citricas, maracuja e final refrescante.',
           NULL,
           true,
           64.90,
           48,
           12.0,
           750,
           6,
           2,
           6,
           1,
           1,
           6
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 6);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-ANT-ROS-2019-750',
           'Antinori Rosato Italiano',
           'Rose seco com aromas de morango, framboesa e final leve para dias quentes.',
           NULL,
           true,
           109.90,
           26,
           12.2,
           750,
           3,
           3,
           5,
           2,
           1,
           3
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 12);

INSERT INTO Produto (
    id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           nextval('produto_id_seq'),
           'VINHO',
           NOW(),
           'VIN-SAL-RIE-2018-750',
           'Salton Riesling Meio Seco',
           'Branco aromatico com dulcor equilibrado, acidez presente e notas de pessego.',
           NULL,
           true,
           59.90,
           38,
           11.5,
           750,
           1,
           2,
           3,
           3,
           3,
           7
       );

INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (currval('produto_id_seq'), 9);

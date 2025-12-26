-- =========================
-- PAÍSES
-- =========================
INSERT INTO Pais (nome, sigla) VALUES ('Brasil', 'BR');
INSERT INTO Pais (nome, sigla) VALUES ('França', 'FR');
INSERT INTO Pais (nome, sigla) VALUES ('Itália', 'IT');
INSERT INTO Pais (nome, sigla) VALUES ('Espanha', 'ES');
INSERT INTO Pais (nome, sigla) VALUES ('Portugal', 'PT');
INSERT INTO Pais (nome, sigla) VALUES ('Chile', 'CL');
INSERT INTO Pais (nome, sigla) VALUES ('Argentina', 'AR');
INSERT INTO Pais (nome, sigla) VALUES ('Estados Unidos', 'US');

-- =========================
-- ESTADOS
-- =========================
INSERT INTO Estado (nome, sigla, regiao) VALUES ('Rio Grande do Sul', 'RS', 4);
INSERT INTO Estado (nome, sigla, regiao) VALUES ('Santa Catarina', 'SC', 4);
INSERT INTO Estado (nome, sigla, regiao) VALUES ('São Paulo', 'SP', 3);
INSERT INTO Estado (nome, sigla, regiao) VALUES ('Minas Gerais', 'MG', 3);

-- =========================
-- CIDADES
-- =========================
INSERT INTO Cidade (nome, estado_id) VALUES ('Bento Gonçalves', 1);
INSERT INTO Cidade (nome, estado_id) VALUES ('Garibaldi', 1);
INSERT INTO Cidade (nome, estado_id) VALUES ('Caxias do Sul', 1);
INSERT INTO Cidade (nome, estado_id) VALUES ('Videira', 2);
INSERT INTO Cidade (nome, estado_id) VALUES ('São Roque', 3);
INSERT INTO Cidade (nome, estado_id) VALUES ('Andradas', 4);

-- =========================
-- MARCAS
-- =========================
INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Miolo', 'Brasil', 1989, 'Premium');

INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Casa Valduga', 'Brasil', 1875, 'Premium');

INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Salton', 'Brasil', 1910, 'Standard');

INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Château Margaux', 'França', 1815, 'Grand Cru');

INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Antinori', 'Itália', 1385, 'Premium');

INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Concha y Toro', 'Chile', 1883, 'Standard');

INSERT INTO Marca (nome, pais_de_origem, ano_fundacao, classificacao)
VALUES ('Catena Zapata', 'Argentina', 1902, 'Premium');

-- =========================
-- ESTILOS
-- =========================
INSERT INTO Estilo (nome) VALUES ('Seco');
INSERT INTO Estilo (nome) VALUES ('Suave');
INSERT INTO Estilo (nome) VALUES ('Meio Seco');
INSERT INTO Estilo (nome) VALUES ('Doce');
INSERT INTO Estilo (nome) VALUES ('Frisante');
INSERT INTO Estilo (nome) VALUES ('Espumante');

-- =========================
-- SAFRAS
-- =========================
INSERT INTO Safra (ano, descricao) VALUES (2020, 'Safra excepcional com clima equilibrado');
INSERT INTO Safra (ano, descricao) VALUES (2019, 'Ótima safra, inverno rigoroso');
INSERT INTO Safra (ano, descricao) VALUES (2018, 'Safra clássica, taninos equilibrados');
INSERT INTO Safra (ano, descricao) VALUES (2017, 'Safra elegante, boa acidez');
INSERT INTO Safra (ano, descricao) VALUES (2016, 'Safra concentrada, grande potencial');
INSERT INTO Safra (ano, descricao) VALUES (2015, 'Safra histórica, qualidade superior');

-- =========================
-- OCASIÕES
-- =========================
INSERT INTO Ocasiao (nome) VALUES ('Jantar Romântico');
INSERT INTO Ocasiao (nome) VALUES ('Churrasco');
INSERT INTO Ocasiao (nome) VALUES ('Confraternização');
INSERT INTO Ocasiao (nome) VALUES ('Celebração');
INSERT INTO Ocasiao (nome) VALUES ('Degustação');
INSERT INTO Ocasiao (nome) VALUES ('Harmonização');
INSERT INTO Ocasiao (nome) VALUES ('Presente');

-- =========================
-- TIPOS DE VINHO
-- =========================
INSERT INTO tipo_vinho (nome) VALUES ('Tinto');
INSERT INTO tipo_vinho (nome) VALUES ('Branco');
INSERT INTO tipo_vinho (nome) VALUES ('Rosé');
INSERT INTO tipo_vinho (nome) VALUES ('Espumante');
INSERT INTO tipo_vinho (nome) VALUES ('Fortificado');
INSERT INTO tipo_vinho (nome) VALUES ('Sobremesa');

-- =========================
-- UVAS
-- =========================
INSERT INTO Uva (nome) VALUES ('Cabernet Sauvignon');
INSERT INTO Uva (nome) VALUES ('Merlot');
INSERT INTO Uva (nome) VALUES ('Tannat');
INSERT INTO Uva (nome) VALUES ('Malbec');
INSERT INTO Uva (nome) VALUES ('Chardonnay');
INSERT INTO Uva (nome) VALUES ('Sauvignon Blanc');
INSERT INTO Uva (nome) VALUES ('Pinot Noir');
INSERT INTO Uva (nome) VALUES ('Moscato');
INSERT INTO Uva (nome) VALUES ('Riesling');
INSERT INTO Uva (nome) VALUES ('Tempranillo');
INSERT INTO Uva (nome) VALUES ('Syrah');
INSERT INTO Uva (nome) VALUES ('Sangiovese');

-- =========================
-- USUÁRIO CLIENTE
-- =========================
INSERT INTO Usuario (nome, login, senha, perfil, ativo)
VALUES (
           'João Silva',
           'joao',
           '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==',
           'CLIENTE',
           true
       );

INSERT INTO Cliente (cpf, email, usuario_id)
VALUES ('12345678901', 'joao.silva@email.com', (SELECT id FROM Usuario WHERE login = 'joao'));

-- =========================
-- ENDEREÇO
-- =========================
INSERT INTO Endereco (cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (
           '77001-000',
           'Quadra 103 Norte',
           10,
           'Apto 201',
           1,
           (SELECT id FROM Cliente WHERE cpf = '12345678901')
       );

-- =========================
-- USUÁRIO ADMIN
-- =========================
INSERT INTO Usuario (nome, login, senha, perfil, ativo)
VALUES (
           'Administrador',
           'admin',
           '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==',
           'ADMIN',
           true
       );

-- =========================
-- FORMAS DE PAGAMENTO
-- =========================
INSERT INTO forma_pagamento (nome, permite_parcelamento, ativo)
VALUES ('Cartão de Crédito', true, true);

INSERT INTO forma_pagamento (nome, permite_parcelamento, ativo)
VALUES ('Cartão de Débito', false, true);

INSERT INTO forma_pagamento (nome, permite_parcelamento, ativo)
VALUES ('PIX', false, true);

INSERT INTO forma_pagamento (nome, permite_parcelamento, ativo)
VALUES ('Boleto Bancário', false, true);

INSERT INTO forma_pagamento (nome, permite_parcelamento, ativo)
VALUES ('Transferência Bancária', false, true);

-- =========================
-- PRODUTO (VINHO)
-- =========================
INSERT INTO Produto (
    tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo,
    preco, estoque_quantidade, teor_alcoolico, volume,
    pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id
)
VALUES (
           'VINHO',
           CURRENT_TIMESTAMP,
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
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (1, 1);
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES (1, 2);
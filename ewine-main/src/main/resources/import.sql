-- =========================================================
-- E-Wine seed inicial - PostgreSQL
-- Texto em ASCII para evitar problemas de encoding.
-- =========================================================

-- =========================================================
-- PAISES
-- =========================================================
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Brasil', 'BR');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Franca', 'FR');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Italia', 'IT');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Espanha', 'ES');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Portugal', 'PT');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Chile', 'CL');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Argentina', 'AR');
INSERT INTO Pais (id, nome, sigla) VALUES (nextval('pais_id_seq'), 'Estados Unidos', 'US');

-- =========================================================
-- ESTADOS E CIDADES
-- =========================================================
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Rio Grande do Sul', 'RS', 4);
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Santa Catarina', 'SC', 4);
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Sao Paulo', 'SP', 3);
INSERT INTO Estado (id, nome, sigla, regiao) VALUES (nextval('estado_id_seq'), 'Minas Gerais', 'MG', 3);

INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Bento Goncalves', (SELECT id FROM Estado WHERE sigla = 'RS'));
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Garibaldi', (SELECT id FROM Estado WHERE sigla = 'RS'));
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Caxias do Sul', (SELECT id FROM Estado WHERE sigla = 'RS'));
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Videira', (SELECT id FROM Estado WHERE sigla = 'SC'));
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Sao Roque', (SELECT id FROM Estado WHERE sigla = 'SP'));
INSERT INTO Cidade (id, nome, estado_id) VALUES (nextval('cidade_id_seq'), 'Andradas', (SELECT id FROM Estado WHERE sigla = 'MG'));

-- =========================================================
-- CATALOGO AUXILIAR
-- =========================================================
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Miolo', 'Brasil', 1989, 'Premium');
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Casa Valduga', 'Brasil', 1875, 'Premium');
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Salton', 'Brasil', 1910, 'Standard');
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Chateau Margaux', 'Franca', 1815, 'Grand Cru');
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Antinori', 'Italia', 1385, 'Premium');
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Concha y Toro', 'Chile', 1883, 'Standard');
INSERT INTO Marca (id, nome, pais_de_origem, ano_fundacao, classificacao) VALUES (nextval('marca_id_seq'), 'Catena Zapata', 'Argentina', 1902, 'Premium');

INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Seco');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Suave');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Meio Seco');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Doce');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Frisante');
INSERT INTO Estilo (id, nome) VALUES (nextval('estilo_id_seq'), 'Espumante');

INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2020, 'Safra com clima equilibrado');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2019, 'Otima safra com inverno rigoroso');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2018, 'Safra classica com taninos equilibrados');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2017, 'Safra elegante com boa acidez');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2016, 'Safra concentrada com grande potencial');
INSERT INTO Safra (id, ano, descricao) VALUES (nextval('safra_id_seq'), 2015, 'Safra historica de qualidade superior');

INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Jantar romantico');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Churrasco');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Confraternizacao');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Celebracao');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Degustacao');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Harmonizacao');
INSERT INTO Ocasiao (id, nome) VALUES (nextval('ocasiao_id_seq'), 'Presente');

INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Tinto');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Branco');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Rose');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Espumante');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Fortificado');
INSERT INTO tipo_vinho (id, nome) VALUES (nextval('tipo_vinho_id_seq'), 'Sobremesa');

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

-- =========================================================
-- USUARIOS, CLIENTES E ENDERECOS
-- Senha padrao: 123
-- =========================================================
INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (nextval('usuario_id_seq'), 'Joao Silva', 'joao', '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'CLIENTE', true);
INSERT INTO Cliente (id, cpf, email, usuario_id)
VALUES (nextval('cliente_id_seq'), '12345678901', 'joao.silva@email.com', (SELECT id FROM Usuario WHERE login = 'joao'));
INSERT INTO Endereco (id, cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (nextval('endereco_id_seq'), '77001-000', 'Quadra 103 Norte', 10, 'Apto 201', (SELECT id FROM Cidade WHERE nome = 'Bento Goncalves'), (SELECT id FROM Cliente WHERE email = 'joao.silva@email.com'));

INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (nextval('usuario_id_seq'), 'Marina Costa', 'marina', '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'CLIENTE', true);
INSERT INTO Cliente (id, cpf, email, usuario_id)
VALUES (nextval('cliente_id_seq'), '23456789012', 'marina.costa@email.com', (SELECT id FROM Usuario WHERE login = 'marina'));
INSERT INTO Endereco (id, cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (nextval('endereco_id_seq'), '01001-000', 'Rua das Palmeiras', 120, 'Casa', (SELECT id FROM Cidade WHERE nome = 'Sao Roque'), (SELECT id FROM Cliente WHERE email = 'marina.costa@email.com'));

INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (nextval('usuario_id_seq'), 'Carlos Mendes', 'carlos', '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'CLIENTE', true);
INSERT INTO Cliente (id, cpf, email, usuario_id)
VALUES (nextval('cliente_id_seq'), '34567890123', 'carlos.mendes@email.com', (SELECT id FROM Usuario WHERE login = 'carlos'));
INSERT INTO Endereco (id, cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (nextval('endereco_id_seq'), '95010-000', 'Avenida Italia', 455, 'Sala 3', (SELECT id FROM Cidade WHERE nome = 'Caxias do Sul'), (SELECT id FROM Cliente WHERE email = 'carlos.mendes@email.com'));

INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (nextval('usuario_id_seq'), 'Ana Rocha', 'ana', '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'CLIENTE', true);
INSERT INTO Cliente (id, cpf, email, usuario_id)
VALUES (nextval('cliente_id_seq'), '45678901234', 'ana.rocha@email.com', (SELECT id FROM Usuario WHERE login = 'ana'));
INSERT INTO Endereco (id, cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (nextval('endereco_id_seq'), '89560-000', 'Rua Videiras', 88, 'Bloco B', (SELECT id FROM Cidade WHERE nome = 'Videira'), (SELECT id FROM Cliente WHERE email = 'ana.rocha@email.com'));

INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (nextval('usuario_id_seq'), 'Beatriz Almeida', 'beatriz', '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'CLIENTE', true);
INSERT INTO Cliente (id, cpf, email, usuario_id)
VALUES (nextval('cliente_id_seq'), '56789012345', 'beatriz.almeida@email.com', (SELECT id FROM Usuario WHERE login = 'beatriz'));
INSERT INTO Endereco (id, cep, logradouro, numero, complemento, cidade_id, cliente_id)
VALUES (nextval('endereco_id_seq'), '37795-000', 'Rua das Flores', 210, 'Apto 12', (SELECT id FROM Cidade WHERE nome = 'Andradas'), (SELECT id FROM Cliente WHERE email = 'beatriz.almeida@email.com'));

INSERT INTO Usuario (id, nome, login, senha, perfil, ativo)
VALUES (nextval('usuario_id_seq'), 'Administrador', 'admin', '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'ADMIN', true);

-- =========================================================
-- FORMAS DE PAGAMENTO
-- =========================================================
INSERT INTO forma_pagamento (id, nome, permite_parcelamento, ativo)
VALUES (nextval('forma_pagamento_id_seq'), 'Cartao de Credito', true, true);
INSERT INTO forma_pagamento (id, nome, permite_parcelamento, ativo)
VALUES (nextval('forma_pagamento_id_seq'), 'PIX', false, true);
INSERT INTO forma_pagamento (id, nome, permite_parcelamento, ativo)
VALUES (nextval('forma_pagamento_id_seq'), 'Boleto Bancario', false, true);

-- =========================================================
-- CUPONS
-- =========================================================
INSERT INTO cupom (id, codigo, percentual_desconto, data_validade, quantidade_maxima_usos, quantidade_usada, ativo)
VALUES (nextval('cupom_id_seq'), 'BEMVINDO10', 10.00, DATE '2027-12-31', 100, 1, true);
INSERT INTO cupom (id, codigo, percentual_desconto, data_validade, quantidade_maxima_usos, quantidade_usada, ativo)
VALUES (nextval('cupom_id_seq'), 'EWINE15', 15.00, DATE '2027-12-31', 80, 1, true);
INSERT INTO cupom (id, codigo, percentual_desconto, data_validade, quantidade_maxima_usos, quantidade_usada, ativo)
VALUES (nextval('cupom_id_seq'), 'FRETEGRATIS', 5.00, DATE '2027-12-31', 150, 1, true);
INSERT INTO cupom (id, codigo, percentual_desconto, data_validade, quantidade_maxima_usos, quantidade_usada, ativo)
VALUES (nextval('cupom_id_seq'), 'VIP20', 20.00, DATE '2027-12-31', 50, 1, true);
INSERT INTO cupom (id, codigo, percentual_desconto, data_validade, quantidade_maxima_usos, quantidade_usada, ativo)
VALUES (nextval('cupom_id_seq'), 'INATIVO25', 25.00, DATE '2025-12-31', 20, 0, false);

-- =========================================================
-- PRODUTOS
-- =========================================================
INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-MIO-CS-2020-750', 'Miolo Reserva Cabernet Sauvignon', 'Vinho tinto com notas de frutas vermelhas e final persistente.', NULL, true, 89.90, 50, 13.5, 750, (SELECT id FROM Pais WHERE sigla = 'BR'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Miolo'), (SELECT id FROM Safra WHERE ano = 2020), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Churrasco'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-MIO-CS-2020-750'), (SELECT id FROM Uva WHERE nome = 'Cabernet Sauvignon'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-MIO-CS-2020-750'), (SELECT id FROM Uva WHERE nome = 'Merlot'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-VAL-MER-2019-750', 'Casa Valduga Terroir Merlot', 'Merlot brasileiro macio com aromas de ameixa e especiarias.', NULL, true, 119.90, 35, 13.0, 750, (SELECT id FROM Pais WHERE sigla = 'BR'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Casa Valduga'), (SELECT id FROM Safra WHERE ano = 2019), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Jantar romantico'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-VAL-MER-2019-750'), (SELECT id FROM Uva WHERE nome = 'Merlot'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-SAL-TAN-2018-750', 'Salton Intenso Tannat', 'Tinto encorpado com taninos marcantes e notas de frutas negras.', NULL, true, 74.90, 42, 13.8, 750, (SELECT id FROM Pais WHERE sigla = 'BR'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Salton'), (SELECT id FROM Safra WHERE ano = 2018), (SELECT id FROM Estilo WHERE nome = 'Meio Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Churrasco'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-SAL-TAN-2018-750'), (SELECT id FROM Uva WHERE nome = 'Tannat'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-MAR-PN-2017-750', 'Chateau Margaux Pinot Noir', 'Pinot Noir frances delicado com cereja fresca e acidez elegante.', NULL, true, 389.90, 12, 12.8, 750, (SELECT id FROM Pais WHERE sigla = 'FR'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Chateau Margaux'), (SELECT id FROM Safra WHERE ano = 2017), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Degustacao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-MAR-PN-2017-750'), (SELECT id FROM Uva WHERE nome = 'Pinot Noir'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-ANT-SAN-2016-750', 'Antinori Chianti Sangiovese', 'Vinho italiano vibrante com notas de cereja e ervas secas.', NULL, true, 159.90, 28, 13.5, 750, (SELECT id FROM Pais WHERE sigla = 'IT'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Antinori'), (SELECT id FROM Safra WHERE ano = 2016), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Harmonizacao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-ANT-SAN-2016-750'), (SELECT id FROM Uva WHERE nome = 'Sangiovese'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-CYT-CAB-2020-750', 'Concha y Toro Cabernet Sauvignon', 'Cabernet chileno com frutas negras, pimenta e boa persistencia.', NULL, true, 69.90, 60, 13.2, 750, (SELECT id FROM Pais WHERE sigla = 'CL'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Concha y Toro'), (SELECT id FROM Safra WHERE ano = 2020), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Churrasco'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-CYT-CAB-2020-750'), (SELECT id FROM Uva WHERE nome = 'Cabernet Sauvignon'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-CAT-MAL-2019-750', 'Catena Zapata Malbec', 'Malbec argentino intenso com ameixa madura e final longo.', NULL, true, 139.90, 31, 14.0, 750, (SELECT id FROM Pais WHERE sigla = 'AR'), (SELECT id FROM tipo_vinho WHERE nome = 'Tinto'), (SELECT id FROM Marca WHERE nome = 'Catena Zapata'), (SELECT id FROM Safra WHERE ano = 2019), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Celebracao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-CAT-MAL-2019-750'), (SELECT id FROM Uva WHERE nome = 'Malbec'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-MIO-CHA-2020-750', 'Miolo Chardonnay Reserva', 'Branco fresco e equilibrado com notas de abacaxi e pera.', NULL, true, 84.90, 44, 12.5, 750, (SELECT id FROM Pais WHERE sigla = 'BR'), (SELECT id FROM tipo_vinho WHERE nome = 'Branco'), (SELECT id FROM Marca WHERE nome = 'Miolo'), (SELECT id FROM Safra WHERE ano = 2020), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Harmonizacao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-MIO-CHA-2020-750'), (SELECT id FROM Uva WHERE nome = 'Chardonnay'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-VAL-MOS-2020-750', 'Casa Valduga Moscatel Espumante', 'Espumante brasileiro leve e refrescante com notas florais.', NULL, true, 94.90, 55, 7.5, 750, (SELECT id FROM Pais WHERE sigla = 'BR'), (SELECT id FROM tipo_vinho WHERE nome = 'Espumante'), (SELECT id FROM Marca WHERE nome = 'Casa Valduga'), (SELECT id FROM Safra WHERE ano = 2020), (SELECT id FROM Estilo WHERE nome = 'Espumante'), (SELECT id FROM Ocasiao WHERE nome = 'Celebracao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-VAL-MOS-2020-750'), (SELECT id FROM Uva WHERE nome = 'Moscato'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-CYT-SBL-2020-750', 'Concha y Toro Sauvignon Blanc', 'Branco chileno com acidez viva, notas citricas e final refrescante.', NULL, true, 64.90, 48, 12.0, 750, (SELECT id FROM Pais WHERE sigla = 'CL'), (SELECT id FROM tipo_vinho WHERE nome = 'Branco'), (SELECT id FROM Marca WHERE nome = 'Concha y Toro'), (SELECT id FROM Safra WHERE ano = 2020), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Harmonizacao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-CYT-SBL-2020-750'), (SELECT id FROM Uva WHERE nome = 'Sauvignon Blanc'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-ANT-ROS-2019-750', 'Antinori Rosato Italiano', 'Rose italiano seco com aromas de morango e final leve.', NULL, true, 109.90, 26, 12.2, 750, (SELECT id FROM Pais WHERE sigla = 'IT'), (SELECT id FROM tipo_vinho WHERE nome = 'Rose'), (SELECT id FROM Marca WHERE nome = 'Antinori'), (SELECT id FROM Safra WHERE ano = 2019), (SELECT id FROM Estilo WHERE nome = 'Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Confraternizacao'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-ANT-ROS-2019-750'), (SELECT id FROM Uva WHERE nome = 'Sangiovese'));

INSERT INTO Produto (id, tipo_produto, data_criacao, sku, nome, descricao, imagem, ativo, preco, estoque_quantidade, teor_alcoolico, volume, pais_id, tipo_vinho_id, marca_id, safra_id, estilo_id, ocasiao_id)
VALUES (nextval('produto_id_seq'), 'VINHO', NOW(), 'VIN-SAL-RIE-2018-750', 'Salton Riesling Meio Seco', 'Branco aromatico com dulcor equilibrado e notas de pessego.', NULL, true, 59.90, 38, 11.5, 750, (SELECT id FROM Pais WHERE sigla = 'BR'), (SELECT id FROM tipo_vinho WHERE nome = 'Branco'), (SELECT id FROM Marca WHERE nome = 'Salton'), (SELECT id FROM Safra WHERE ano = 2018), (SELECT id FROM Estilo WHERE nome = 'Meio Seco'), (SELECT id FROM Ocasiao WHERE nome = 'Presente'));
INSERT INTO vinho_uva (vinho_id, uva_id) VALUES ((SELECT id FROM Produto WHERE sku = 'VIN-SAL-RIE-2018-750'), (SELECT id FROM Uva WHERE nome = 'Riesling'));

-- =========================================================
-- PEDIDOS, PAGAMENTOS, ITENS E HISTORICO
-- A tabela pedido nao possui subtotal/total; o backend calcula por itens e desconto.
-- =========================================================

-- Pedido 1: Joao - aguardando pagamento
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 179.80, 1, 179.80, (SELECT id FROM forma_pagamento WHERE nome = 'PIX'), 'PENDENTE', NULL, NOW() - INTERVAL '12 days', NULL);
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'joao'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'joao' LIMIT 1), currval('pagamento_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '12 days', NULL, NULL, 0.00);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto)
VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-MIO-CS-2020-750'), 2, 89.90, 'Miolo Reserva Cabernet Sauvignon');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao)
VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '12 days', 'Pedido criado e aguardando pagamento');

-- Pedido 2: Marina - pago com cupom
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 224.73, 1, 224.73, (SELECT id FROM forma_pagamento WHERE nome = 'PIX'), 'CONFIRMADO', 'PIX-SEED-0002', NOW() - INTERVAL '10 days', NOW() - INTERVAL '10 days' + INTERVAL '5 minutes');
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'marina'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'marina' LIMIT 1), currval('pagamento_id_seq'), 'PAGO', NOW() - INTERVAL '10 days', NULL, (SELECT id FROM cupom WHERE codigo = 'BEMVINDO10'), 24.97);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto)
VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-VAL-MER-2019-750'), 1, 119.90, 'Casa Valduga Terroir Merlot');
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto)
VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-CYT-SBL-2020-750'), 2, 64.90, 'Concha y Toro Sauvignon Blanc');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '10 days', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'PAGO', NOW() - INTERVAL '10 days' + INTERVAL '5 minutes', 'Pagamento aprovado');

-- Pedido 3: Carlos - em separacao com cupom
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 223.84, 2, 111.92, (SELECT id FROM forma_pagamento WHERE nome = 'Cartao de Credito'), 'CONFIRMADO', 'CC-SEED-0003', NOW() - INTERVAL '8 days', NOW() - INTERVAL '8 days' + INTERVAL '3 minutes');
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'carlos'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'carlos' LIMIT 1), currval('pagamento_id_seq'), 'EM_SEPARACAO', NOW() - INTERVAL '8 days', NULL, (SELECT id FROM cupom WHERE codigo = 'VIP20'), 55.96);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto)
VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-CAT-MAL-2019-750'), 2, 139.90, 'Catena Zapata Malbec');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '8 days', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'PAGO', NOW() - INTERVAL '8 days' + INTERVAL '3 minutes', 'Pagamento aprovado');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'EM_SEPARACAO', NOW() - INTERVAL '7 days', 'Pedido em separacao');

-- Pedido 4: Ana - saiu para entrega com cupom
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 292.91, 1, 292.91, (SELECT id FROM forma_pagamento WHERE nome = 'PIX'), 'CONFIRMADO', 'PIX-SEED-0004', NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days' + INTERVAL '2 minutes');
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'ana'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'ana' LIMIT 1), currval('pagamento_id_seq'), 'SAIU_PARA_ENTREGA', NOW() - INTERVAL '6 days', NULL, (SELECT id FROM cupom WHERE codigo = 'EWINE15'), 51.69);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-VAL-MOS-2020-750'), 3, 94.90, 'Casa Valduga Moscatel Espumante');
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-SAL-RIE-2018-750'), 1, 59.90, 'Salton Riesling Meio Seco');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '6 days', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'PAGO', NOW() - INTERVAL '6 days' + INTERVAL '2 minutes', 'Pagamento aprovado');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'EM_SEPARACAO', NOW() - INTERVAL '5 days', 'Pedido em separacao');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'SAIU_PARA_ENTREGA', NOW() - INTERVAL '4 days', 'Pedido saiu para entrega');

-- Pedido 5: Beatriz - entregue
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 609.70, 3, 203.23, (SELECT id FROM forma_pagamento WHERE nome = 'Cartao de Credito'), 'CONFIRMADO', 'CC-SEED-0005', NOW() - INTERVAL '18 days', NOW() - INTERVAL '18 days' + INTERVAL '4 minutes');
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'beatriz'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'beatriz' LIMIT 1), currval('pagamento_id_seq'), 'ENTREGUE', NOW() - INTERVAL '18 days', NOW() - INTERVAL '12 days', NULL, 0.00);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-MAR-PN-2017-750'), 1, 389.90, 'Chateau Margaux Pinot Noir');
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-ANT-ROS-2019-750'), 2, 109.90, 'Antinori Rosato Italiano');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '18 days', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'PAGO', NOW() - INTERVAL '18 days' + INTERVAL '4 minutes', 'Pagamento aprovado');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'EM_SEPARACAO', NOW() - INTERVAL '17 days', 'Pedido em separacao');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'SAIU_PARA_ENTREGA', NOW() - INTERVAL '15 days', 'Pedido saiu para entrega');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'ENTREGUE', NOW() - INTERVAL '12 days', 'Pedido entregue ao cliente');

-- Pedido 6: Joao - cancelado
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 74.90, 1, 74.90, (SELECT id FROM forma_pagamento WHERE nome = 'Boleto Bancario'), 'RECUSADO', 'BOL-SEED-0006', NOW() - INTERVAL '3 days', NULL);
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'joao'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'joao' LIMIT 1), currval('pagamento_id_seq'), 'CANCELADO', NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days', NULL, 0.00);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-SAL-TAN-2018-750'), 1, 74.90, 'Salton Intenso Tannat');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '3 days', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'CANCELADO', NOW() - INTERVAL '2 days', 'Pedido cancelado');

-- Pedido 7: Marina - entregue
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 239.70, 1, 239.70, (SELECT id FROM forma_pagamento WHERE nome = 'PIX'), 'CONFIRMADO', 'PIX-SEED-0007', NOW() - INTERVAL '25 days', NOW() - INTERVAL '25 days' + INTERVAL '2 minutes');
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'marina'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'marina' LIMIT 1), currval('pagamento_id_seq'), 'ENTREGUE', NOW() - INTERVAL '25 days', NOW() - INTERVAL '20 days', NULL, 0.00);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-MIO-CHA-2020-750'), 2, 84.90, 'Miolo Chardonnay Reserva');
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-CYT-CAB-2020-750'), 1, 69.90, 'Concha y Toro Cabernet Sauvignon');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '25 days', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'PAGO', NOW() - INTERVAL '25 days' + INTERVAL '2 minutes', 'Pagamento aprovado');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'EM_SEPARACAO', NOW() - INTERVAL '24 days', 'Pedido em separacao');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'SAIU_PARA_ENTREGA', NOW() - INTERVAL '22 days', 'Pedido saiu para entrega');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'ENTREGUE', NOW() - INTERVAL '20 days', 'Pedido entregue ao cliente');

-- Pedido 8: Carlos - pago com cupom
INSERT INTO pagamento (id, valor_total, parcelas, valor_parcela, forma_pagamento_id, status, transacao_externa_id, data_criacao, data_confirmacao)
VALUES (nextval('pagamento_id_seq'), 374.97, 2, 187.49, (SELECT id FROM forma_pagamento WHERE nome = 'Cartao de Credito'), 'CONFIRMADO', 'CC-SEED-0008', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day' + INTERVAL '1 minute');
INSERT INTO pedido (id, cliente_id, endereco_entrega_id, pagamento_id, status, data_criacao, data_finalizacao, cupom_id, desconto)
VALUES (nextval('pedido_id_seq'), (SELECT c.id FROM Cliente c JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'carlos'), (SELECT e.id FROM Endereco e JOIN Cliente c ON c.id = e.cliente_id JOIN Usuario u ON u.id = c.usuario_id WHERE u.login = 'carlos' LIMIT 1), currval('pagamento_id_seq'), 'PAGO', NOW() - INTERVAL '1 day', NULL, (SELECT id FROM cupom WHERE codigo = 'FRETEGRATIS'), 19.73);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-ANT-SAN-2016-750'), 1, 159.90, 'Antinori Chianti Sangiovese');
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-CAT-MAL-2019-750'), 1, 139.90, 'Catena Zapata Malbec');
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, nome_produto) VALUES (nextval('item_pedido_id_seq'), currval('pedido_id_seq'), (SELECT id FROM Produto WHERE sku = 'VIN-VAL-MOS-2020-750'), 1, 94.90, 'Casa Valduga Moscatel Espumante');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL '1 day', 'Pedido criado e aguardando pagamento');
INSERT INTO pedido_historico (id, pedido_id, status, data, descricao) VALUES (nextval('pedido_historico_id_seq'), currval('pedido_id_seq'), 'PAGO', NOW() - INTERVAL '1 day' + INTERVAL '1 minute', 'Pagamento aprovado');

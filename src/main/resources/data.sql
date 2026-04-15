-- Script de inicialização do banco de dados H2
-- Popula a tabela de equipamentos com dados de exemplo
-- Este script é executado APÓS o JPA criar as tabelas

-- Insere equipamentos de exemplo
INSERT INTO equipamento (descricao, ativo) VALUES ('Datashow Epson PowerLite X41+', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Notebook Dell Inspiron 15', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Projetor BenQ MH535A', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Microfone sem fio Shure SM58', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Caixa de som JBL Partybox 310', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Câmera Canon EOS Rebel T7', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Tripé Manfrotto Compact Action', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Lousa Digital Interativa 77 polegadas', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Tablet Samsung Galaxy Tab S7', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Impressora HP LaserJet Pro M404dn', false);
INSERT INTO equipamento (descricao, ativo) VALUES ('Scanner Epson WorkForce DS-530', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Webcam Logitech C920 HD Pro', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Headset Jabra Evolve 75', false);
INSERT INTO equipamento (descricao, ativo) VALUES ('Roteador Wi-Fi TP-Link Archer AX50', true);
INSERT INTO equipamento (descricao, ativo) VALUES ('Switch Cisco Catalyst 2960', true);

-- Mensagem de confirmação (apenas para log)
SELECT 'Dados de equipamentos inseridos com sucesso!' AS status;


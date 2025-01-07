use all2you;

--preencher tabelas 
INSERT INTO tipo_unidade (unidade, descricao)
VALUES
('UN'         ,'Unidade'),
('LT'         ,'Litro'),
('ML'         ,'Metros Lineares'),
('KG'         ,'Kilogramas'),
('MT'         ,'Metros');

INSERT INTO tipo_operacao (id_tp_operacao, descricao)
VALUES
('Soldadura'           ,''),
('Pintura'             ,''),
('Embalagem'           ,''),
('Quinagem'            ,''),
('Corte Laser'         ,''),	
('Embalagem Prato'     ,''),
('Embalagem Acessorios',''),
('Embalagem Final'     ,''),
('Estampagem'          ,''),
('Furacao e Corte'     ,''),
('Recorte'             ,'');

-- 
INSERT INTO operador ( nome, estado) 
VALUES 
('Dionisio Sousa' ,0),
('Jorge Fernandes',1),
('Ines Soares'    ,1);	

--
INSERT INTO maquina (id_maquina, id_tp_operacao, estado)
VALUES
('MMPRLE019999','Embalagem Prato',     '1'),
('MMPRBE019988','Embalagem Acessorios','1'),
('MMPRBE019988','Embalagem Final',     '1'),
('MMPRLP019999','Pintura',             '1'),
('MMPRLP019999','Furacao e Corte',     '1'),
('MMPRLM019999','Estampagem',          '0');

-- 
INSERT INTO produto (ref_produto, versao_produto, designacao, designacao_comercial, qtd_lote, unidade, estado) 
VALUES 
('A060PNRSCN109' ,'2020A'         ,'Antena Panoramica 46X60 SCN RAL 7016','Antena Panoramica 46X60 SCN RAL 7016 TEXT. (Cinza Escuro)',1,'UN','1'),
('CPRT0580060101','Base Cinza'    ,'Prato OFP 53 SEB Galva 0,6 C/Pintura','PRATO PNR'                                                ,1,'UN','1'),
('CPRT0593060100','Base S/Pintura','Prato OFP 53 SEB Galva 0,6  S/Pintura','Prato OFP 53 SEB Galva 0,6 S/Pintura'                    ,1,'UN','1');

--
INSERT INTO operacao (id_tp_operacao, ref_produto, versao_produto, ordem, descricao, mao_de_obra, tempo, instrucoes_tecnicas) 
VALUES 
('Embalagem Prato'     ,'A060PNRSCN109' ,'2020A'         ,1,null,2,1500,null),
('Embalagem Acessorios','A060PNRSCN109' ,'2020A'         ,2,null,4,300 ,null),
('Embalagem Final'     ,'A060PNRSCN109' ,'2020A'         ,3,null,1,60  ,null),
('Pintura'             ,'CPRT0580060101','Base Cinza'    ,1,null,5,5,null ),
('Estampagem'          ,'CPRT0593060100','Base S/Pintura',1,null,2,11,null),
('Furacao e Corte'     ,'CPRT0593060100','Base S/Pintura',1,null,5,5,null)
;

--
INSERT INTO componente (ref, versao, id_operacao, designacao_comercial, quantidade, unidade, alternativa)
VALUES
('CPRT0580060101','Base Cinza'    , 1,'PRATO PNR'                                    ,320  ,'UN',''             ),
('BBOL042301000' ,'Base'          , 2,'Bolsa Acessorios PNR'                         ,320  ,'UN',''             ),
('EBFE000303000' ,'A'             , 3,'Bobine Filme Extiravel Manual 500x0,23mm'     ,0.1  ,'KG',''             ),
('EBCC000101000' ,'A'             , 1,'Cartao Canelado 1200mm'                       ,0.25 ,'KG','EBCC000101001'),
('EBFE000103000' ,'A'             , 3,'Bobine Filme Extiravel Automatico 500x0,23mm' ,0.17 ,'KG',''             ),
('CPRT0593060100','Base S/Pintura', 4,'Prato OFP 53 SEB Galva 0,6 S/Pintura'         ,1    ,'UN',''             ),
('QPIN000301999' ,'Base'          , 4,'Po Ibemix'                                    ,0.094,'KG',''             ),
('PFOR012604900' ,'Base'          , 5,'Formato 620x680x0,6mm DC03'                   ,1.986,'KG',''             );


-- Enunciado V2

INSERT INTO tipo_contacto (	tipo ,descricao )
VALUES
('e-mail','e-mail pessoal'),
('telefone','telefone fixo'),
('telemóvel','telemóvel da empresa');

-- Horario
INSERT INTO tipo_dia (	dia )
VALUES
('Domingo'),
('Segunda'),
('Terca'),
('Quarta'),
('Quinta'),
('Sexta'),
('Sabado');

-- Cliente
INSERT INTO cliente (nif, nome)
VALUES
('12345678', 'Marlene Lima'),
('87654321', 'Beatriz Alexandre'), 
('63524187', 'Rute Lontro'),
('36251478', 'Isabel Jesus'),
('14253687', 'Miguel Salgado'),
('270298729', 'Catia Fonseca'),
('281298587','Jorge Pereira'),
('219166221', 'Nuno Ventura');


-- MoradaCliente
INSERT INTO morada_cliente (morada, codigo_postal, localidade, pais, morada_fiscal, nif)
VALUES
('Rua do Norte n.37', '4740-142', 'Apúlia', 'Portugal', '1', '12345678'),
('Av. Dr. Domingos Caetano Sousa nº522 1ºE', '4520-212', 'Santa Maria Da Feira', 'Portugal', '0', '12345678'),
('Rua do Beco nº5', '4123-102', 'Caranguejeira', 'Portugal', '1', '87654321'),
('Av. 25 de Abril nº105', '4520-212', 'Santa Maria da Feira', 'Portugal', '0', '87654321'),
('Rua de cima nº 1','4415-870', 'Sandim','Portugal', 1,'270298729'),
('Rua do ouro nº 375','4000-285', 'Porto','Portugal', 1,'281298587'),
('Av da liberdade nº 5478','2685-038', 'Sacavem','Portugal', 1,'219166221');

-- ContactoCliente
INSERT INTO contacto_cliente (nif, contacto, tipo_contacto)
VALUES
('12345678', '917539038', 'telemóvel'),
('12345678', '253982877', 'telefone'),
('12345678', 'marlene23@gmail.com', 'e-mail'),
('87654321', '918564325', 'telemóvel'),
('87654321', 'biaalex@gmail.com', 'e-mail'),
('14253687', 'msalgado@gmail.com', 'e-mail'),
('270298729','catia.fonseca@gmail.com','e-mail'),
('270298729','210 018 000','telefone'),
('281298587','914895477','telemóvel'),
('219166221','939784108','telemóvel');



INSERT INTO tipo_desconto (descricao)
VALUES
('Percentagem'),
('Valor');

INSERT INTO tipo_estado (descricao)
VALUES
('Por validar'),
('Registada'),
('Finalizada'),
('Entregue');

INSERT INTO operador_horario (id_operador, dia, hora_inicio, hora_fim)
VALUES
('1','Domingo', '10:00:00.0000000', '18:00:00.0000000'),
('1', 'Terca', '14:00:00.0000000', '00:00:00.0000000'),
('1', 'Quinta', '02:00:00.0000000', '08:00:00.0000000'),
('2', 'Segunda', '20:00:00.0000000', '23:00:00.0000000'),
('2', 'Quarta', '08:00:00.0000000', '16:00:00.0000000'),
('2', 'Sexta', '14:00:00.0000000', '19:00:00.0000000');

INSERT INTO maquina_horario (id_maquina, id_tp_operacao, dia, hora_inicio, hora_fim)
VALUES
('MMPRBE019988','Embalagem Final', 'Domingo', '10:00:00.0000000', '18:00:00.0000000'),
('MMPRBE019988','Embalagem Final', 'Terca', '14:00:00.0000000', '00:00:00.0000000'),
('MMPRBE019988','Embalagem Final', 'Quinta', '02:00:00.0000000', '08:00:00.0000000'),
('MMPRLP019999','Pintura', 'Segunda', '20:00:00.0000000', '23:00:00.0000000'),
('MMPRLP019999','Pintura', 'Quarta', '08:00:00.0000000', '16:00:00.0000000'),
('MMPRLP019999','Pintura', 'Sexta', '14:00:00.0000000', '19:00:00.0000000');

-- Encomenda

INSERT INTO encomenda (id_encomenda, nif, id_morada_entrega, data_documento, tipo_desconto, valor_desconto, data_criacao, data_modificaçao)
VALUES
('M8GHD', '12345678', '1','2021-08-03', '0', '5', '2025-02-10', '2025-04-12'),
('DS5BX','87654321','2','2021-09-03', '1', '5', '2025-12-10', '2025-03-12');

-- Linha Encomenda
INSERT INTO linha_encomenda (id_encomenda, ref_produto, versao_produto, descricao, quantidade, preco_unitario)
VALUES
('M8GHD', 'A060PNRSCN109',  '2020A', 'Antena Panoramica 46X60 SCN RAL 7016', '10', '50'),
('DS5BX', 'CPRT0593060100',  'Base S/Pintura', 'Prato OFP 53 SEB Galva 0,6  S/Pintura', '60', '1000');

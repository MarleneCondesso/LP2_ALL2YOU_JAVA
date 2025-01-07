/* REMOTO*/
/*
use LP2_g2_2021;
go
*/

/* lOCAL*/
CREATE DATABASE all2you;
go

use all2you;
go

--criar tabelas
CREATE TABLE operador (
    id_operador		INT IDENTITY(0,1) CONSTRAINT pk_operador_id_operador PRIMARY KEY NOT NULL ,
    nome			NVARCHAR(50) NOT NULL,
    estado			BIT NOT NULL
)
GO

CREATE TABLE operacao (
    id_operacao		INT IDENTITY(0,1) CONSTRAINT pk_operacao_id_operacao PRIMARY KEY NOT NULL ,
	id_tp_operacao	NVARCHAR(50) NOT NULL,
	ref_produto		NVARCHAR(100) NOT NULL,
	versao_produto	NVARCHAR(50) NOT NULL,
	ordem			SMALLINT NOT NULL,
	descricao		NVARCHAR(200),
	mao_de_obra		SMALLINT NOT NULL,
	tempo			INT NOT NULL,
	instrucoes_tecnicas	NVARCHAR(200),
)
GO

CREATE TABLE tarefa_operador (
	id_operador INTEGER  NOT NULL,
    id_operacao INTEGER  NOT NULL,
	data_inicio DATETIME NOT NULL,
	data_fim	DATETIME NOT NULL,
)
GO

CREATE TABLE tipo_unidade (
    unidade NVARCHAR(2) CONSTRAINT pk_tipo_unidade_unidade PRIMARY KEY NOT NULL ,
    descricao NVARCHAR(30) NOT NULL
)
GO

CREATE TABLE produto (
	ref_produto	NVARCHAR(100) NOT NULL,
	versao_produto  NVARCHAR(50) NOT NULL,
	designacao		NVARCHAR(100) NOT NULL,
	designacao_comercial NVARCHAR(100) NOT NULL,
	qtd_lote INTEGER NOT NULL,
	unidade NVARCHAR(2) NOT NULL,
	estado bit NOT NULL
CONSTRAINT pk_produto PRIMARY KEY (ref_produto asc, versao_produto asc),
CONSTRAINT FK_produto_tipo_unidade FOREIGN KEY (unidade) REFERENCES tipo_unidade(unidade)
)
GO


CREATE TABLE tipo_operacao (
    id_tp_operacao	NVARCHAR(50) CONSTRAINT pk_tipo_operacao_id_tp_operacao PRIMARY KEY NOT NULL ,
    descricao NVARCHAR(100)
)
GO

CREATE TABLE maquina (
    id_maquina NVARCHAR(20) NOT NULL,
	id_tp_operacao	NVARCHAR(50) NOT NULL,
	estado  bit NOT NULL,
CONSTRAINT FK_maquina_tipo_operacao FOREIGN KEY (id_tp_operacao) REFERENCES tipo_operacao(id_tp_operacao),
CONSTRAINT PK_maquina PRIMARY KEY (id_maquina, id_tp_operacao)
)
GO

CREATE TABLE tarefa_maquina (
    id_operacao INTEGER NOT NULL,
	id_maquina NVARCHAR(20) NOT NULL,
	id_tp_operacao NVARCHAR(50) NOT NULL,
	data_inicio DATETIME NOT NULL,
	data_fim	DATETIME NOT NULL,
CONSTRAINT FK_tarefa_maquina_operacao FOREIGN KEY (id_operacao) REFERENCES operacao(id_operacao),
CONSTRAINT FK_tarefa_maquina_maquina  FOREIGN KEY (id_maquina,id_tp_operacao)  REFERENCES maquina(id_maquina,id_tp_operacao),
CONSTRAINT PK_tarefa_maquina PRIMARY KEY (id_operacao, id_maquina, id_tp_operacao, data_inicio)
)
GO

CREATE TABLE componente (
    ref				NVARCHAR(100) NOT NULL,
	versao			NVARCHAR(50) NOT NULL,
	id_operacao		INTEGER NOT NULL,
	designacao_comercial    NVARCHAR(100) NOT NULL,
	quantidade		DECIMAL(10,4) NOT NULL,
	unidade			NVARCHAR(2) NOT NULL,
	alternativa		NVARCHAR(25)
CONSTRAINT pk_componente PRIMARY KEY (ref asc, versao asc),
CONSTRAINT FK_componente_operacao  FOREIGN KEY (id_operacao)  REFERENCES operacao(id_operacao),
CONSTRAINT FK_componente_tipo_unidade FOREIGN KEY (unidade) REFERENCES tipo_unidade(unidade)
)
GO

CREATE TABLE cliente(
	nif INTEGER CONSTRAINT pk_cliente_nif PRIMARY KEY NOT NULL,
	nome NVARCHAR(50) NOT NULL,
)
GO

CREATE TABLE morada_cliente (
	id INT IDENTITY(0,1) CONSTRAINT pk_morada_cliente_id PRIMARY KEY NOT NULL ,
	morada NVARCHAR(100) NOT NULL,
	codigo_postal CHAR(8) CONSTRAINT CK_Morada_cliente_codigo_postal CHECK( codigo_postal LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9]') NOT NULL,
	localidade NVARCHAR(100) NOT NULL,
	pais NVARCHAR(100) NOT NULL,
  morada_fiscal BIT DEFAULT 0,
	nif INTEGER CONSTRAINT fk_morada_cliente_cliente FOREIGN KEY (nif) REFERENCES cliente(nif) NOT NULL
)
GO

CREATE TABLE tipo_desconto (
	id INT IDENTITY(0,1) CONSTRAINT pk_tipo_desconto_id PRIMARY KEY NOT NULL ,
	descricao NVARCHAR(50) NOT NULL
)
GO

CREATE TABLE encomenda (
    id_encomenda NVARCHAR(15) CONSTRAINT pk_encomenda_id_encomenda PRIMARY KEY NOT NULL,
	nif INTEGER CONSTRAINT fk_encomenda_nif FOREIGN KEY (nif) REFERENCES CLIENTE(nif) NOT NULL ,
	id_morada_entrega INTEGER CONSTRAINT fk_encomenda_id_morada_entrega FOREIGN KEY (id_morada_entrega) REFERENCES morada_cliente(id) NOT NULL ,
	data_documento DATE NOT NULL,
	tipo_desconto  INTEGER CONSTRAINT fk_encomenda_tipo_desconto FOREIGN KEY (tipo_desconto) REFERENCES tipo_desconto(id) NOT NULL ,
	valor_desconto	NUMERIC(10,2) CONSTRAINT CK_encomenda_valor_desconto CHECK (valor_desconto > 0),
	data_criacao		DATE NOT NULL,
	data_modificaçao	DATE NOT NULL
)
GO

CREATE TABLE linha_encomenda(
    id_encomenda NVARCHAR(15) NOT NULL ,
	ref_produto	NVARCHAR(100) NOT NULL,
	versao_produto NVARCHAR(50) NOT NULL,
	descricao NVARCHAR(100) NOT NULL,
	quantidade INTEGER NOT NULL,
	preco_unitario  NUMERIC(10,2) CONSTRAINT CK_linha_encomenda_preco_unitario CHECK (preco_unitario > 0)
CONSTRAINT FK_linha_encomenda_encomenda  FOREIGN KEY (id_encomenda)    REFERENCES encomenda(id_encomenda),
CONSTRAINT FK_linha_encomenda_produto	 FOREIGN KEY (ref_produto,versao_produto)
REFERENCES produto(ref_produto,versao_produto),
CONSTRAINT pk_linha_encomenda PRIMARY KEY (id_encomenda,ref_produto,versao_produto)
)
GO

CREATE TABLE tipo_estado (
	id INT IDENTITY(0,1) CONSTRAINT pk_tipo_estado_id PRIMARY KEY NOT NULL ,
	descricao NVARCHAR(50) NOT NULL
)
GO

CREATE TABLE estado_encomenda(
	id_encomenda NVARCHAR(15) CONSTRAINT FK_estado_encomenda_encomenda  FOREIGN KEY (id_encomenda)    REFERENCES encomenda(id_encomenda) NOT NULL,
	id_estado INTEGER    CONSTRAINT fk_estado_encomenda_tipo_estado FOREIGN KEY (id_estado) REFERENCES tipo_estado(id) NOT NULL ,
	fechado BIT NOT NULL DEFAULT '0',
	data_ultima_modifacao DATE NOT NULL,
	CONSTRAINT pk_estado_encomenda PRIMARY KEY (id_encomenda,id_estado)
)
GO

ALTER TABLE operacao
ADD CONSTRAINT FK_operacao_tipo_operacao FOREIGN KEY (id_tp_operacao) REFERENCES tipo_operacao(id_tp_operacao),
CONSTRAINT FK_operacao_produto FOREIGN KEY (ref_produto,versao_produto) REFERENCES produto(ref_produto,versao_produto);

ALTER TABLE tarefa_operador
ADD CONSTRAINT FK_tarefa_operador_operador FOREIGN KEY (id_operador) REFERENCES operador(id_operador),
CONSTRAINT FK_tarefa_operador_operacao FOREIGN KEY (id_operacao) REFERENCES operacao(id_operacao),
CONSTRAINT PK_tarefa_operador PRIMARY KEY (id_operador, id_operacao,data_inicio);

--
CREATE TABLE nota_cliente (
	id INT IDENTITY(0,1) CONSTRAINT pk_nota_cliente_id PRIMARY KEY NOT NULL ,
	nif INTEGER CONSTRAINT fk_nota_cliente_cliente FOREIGN KEY (nif) REFERENCES CLIENTE(nif) NOT NULL ,
	nota NVARCHAR(200)
)
GO

CREATE TABLE tipo_contacto (
	tipo NVARCHAR(20) CONSTRAINT pk_Tipo_Contacto_tipo PRIMARY KEY NOT NULL,
	descricao NVARCHAR(20) NOT NULL
)
GO

CREATE TABLE contacto_cliente (
	nif INTEGER CONSTRAINT fk_contacto_Cliente_cliente FOREIGN KEY (nif) REFERENCES CLIENTE(nif) NOT NULL ,
	contacto NVARCHAR(50) NOT NULL ,
	tipo_contacto NVARCHAR(20) CONSTRAINT fk_contacto_cliente_tipo_contacto FOREIGN KEY (tipo_contacto) REFERENCES tipo_contacto(tipo)NOT NULL
	CONSTRAINT pk_contacto_Cliente PRIMARY KEY (contacto, tipo_contacto)
)
GO

CREATE TABLE observacao_contacto_cliente (
	id INT IDENTITY(0,1) CONSTRAINT pk_observacao_contacto_cliente_id PRIMARY KEY NOT NULL ,
	contacto NVARCHAR(20) CONSTRAINT fk_observacao_contacto_cliente_contacto FOREIGN KEY (contacto) REFERENCES tipo_contacto(tipo)NOT NULL,
	tipo_contacto NVARCHAR(20) CONSTRAINT fk_observacao_contacto_cliente_tipo_contacto FOREIGN KEY (tipo_contacto) REFERENCES tipo_contacto(tipo)NOT NULL,
	observacao  NVARCHAR(500) NOT NULL
)
GO

-- Horario
CREATE TABLE tipo_dia (
	dia NVARCHAR(10) CONSTRAINT pk_dia_semana_dia PRIMARY KEY NOT NULL
)
GO

CREATE TABLE operador_horario (
	id_operador		INTEGER CONSTRAINT fk_operador_horario_operador FOREIGN KEY (id_operador) REFERENCES operador(id_operador) NOT NULL,
	dia NVARCHAR(10) CONSTRAINT fk_operador_horario_dia FOREIGN KEY (dia) REFERENCES tipo_dia(dia)NOT NULL,
	hora_inicio TIME(0) NOT NULL,
	hora_fim	TIME(0) NOT NULL
	CONSTRAINT pk_operador_horario PRIMARY KEY (id_operador,dia, hora_inicio)
)
GO

CREATE TABLE maquina_horario (
    id_maquina NVARCHAR(20)		 NOT NULL,
	id_tp_operacao	NVARCHAR(50) NOT NULL,
	dia NVARCHAR(10) CONSTRAINT fk_maquina_horario_dia FOREIGN KEY (dia) REFERENCES tipo_dia(dia)NOT NULL,
	hora_inicio TIME(0) NOT NULL,
	hora_fim	TIME(0) NOT NULL
	CONSTRAINT pk_maquina_horario PRIMARY KEY (id_maquina, id_tp_operacao,dia, hora_inicio),
	CONSTRAINT fk_maquina_horario_maquina_id_maquina     FOREIGN KEY (id_maquina,id_tp_operacao) REFERENCES maquina(id_maquina,id_tp_operacao)
)
GO

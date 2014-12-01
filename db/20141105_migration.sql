/** TABELA faculdade **/

CREATE TABLE faculdade
(
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(50) NOT NULL,
	CONSTRAINT PK_faculdade PRIMARY KEY (id)
);

/** TABELA local **/

CREATE TABLE local
(
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(50) NOT NULL,
	CONSTRAINT PK_local PRIMARY KEY (id)
);

/** TABELA ocupacao **/

CREATE TABLE ocupacao
(
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(50) NOT NULL,
	CONSTRAINT PK_ocupacao PRIMARY KEY (id)
);

/** TABELA usuario **/

CREATE TABLE usuario
(
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(50) NOT NULL,
	sobrenome varchar(50) NOT NULL,
	data_nascimento date,
	sexo varchar(1),
	ddd tinyint,
	telefone numeric(9), 
	email varchar(255) NOT NULL,
	senha varchar(60) NOT NULL, 
	promotor boolean NOT NULL,
	faculdade_id int,
	ocupacao_id int NOT NULL,
	CONSTRAINT PK_usuario PRIMARY KEY (id),
	CONSTRAINT FK_usuario_faculdade FOREIGN KEY (faculdade_id) REFERENCES faculdade(id),
	CONSTRAINT FK_usuario_ocupacao FOREIGN KEY (ocupacao_id) REFERENCES ocupacao(id),
	UNIQUE (email)
);

/** TABELA evento **/

CREATE TABLE evento
(
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(255) NOT NULL,
	inicio datetime NOT NULL,
	final datetime,
	email varchar(50) NOT NULL,
	ddd tinyint,
	telefone numeric(9),
	descricao varchar(2047),
	criado_por int NOT NULL,
	local_id int NOT NULL,
	CONSTRAINT PK_evento PRIMARY KEY (id),
	CONSTRAINT FK_evento_usuario FOREIGN KEY (criado_por) REFERENCES usuario(id),
	CONSTRAINT FK_local FOREIGN KEY (local_id) REFERENCES local(id)
);

/** TABELA tag **/

CREATE TABLE tag
(
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(20) NOT NULL,
	CONSTRAINT PK_tag PRIMARY KEY (id)
);

/** TABELA participante **/

CREATE TABLE participante
(
	id int NOT NULL AUTO_INCREMENT,
	usuario_id int NOT NULL,
	evento_id int NOT NULL,
	CONSTRAINT PK_participante PRIMARY KEY (id),
	CONSTRAINT FK_participante_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
	CONSTRAINT FK_participante_evento FOREIGN KEY (evento_id) REFERENCES evento(id),
	CONSTRAINT UC_participante UNIQUE (usuario_id, evento_id)
);

/** TABELA tageamento **/

CREATE TABLE tagueamento
(
	id int NOT NULL AUTO_INCREMENT,
	tag_id int NOT NULL,
	evento_id int NOT NULL,
	CONSTRAINT PK_tagueamento PRIMARY KEY (id),
	CONSTRAINT FK_tagueamento_tag FOREIGN KEY (tag_id) REFERENCES tag(id),
	CONSTRAINT FK_tagueamento_evento FOREIGN KEY (evento_id) REFERENCES evento(id),
	CONSTRAINT UC_tagueamento UNIQUE (tag_id, evento_id)
);

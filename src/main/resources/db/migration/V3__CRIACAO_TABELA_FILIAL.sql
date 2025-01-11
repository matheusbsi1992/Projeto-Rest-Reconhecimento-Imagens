CREATE TABLE estudos.filial (
                id_filial  CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
                id_unidade CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
                id_endereco INTEGER,
                razao_social_filial VARCHAR,
                nome_fantasia_filial VARCHAR,
                documento_filial VARCHAR,
                email_filial VARCHAR,
                telefone_filial VARCHAR,
                setor_filial VARCHAR,
                status_filial Boolean,
                data_cadastro_filial TIMESTAMP,
                data_atualizado_filial TIMESTAMP,
                CONSTRAINT filial_pk PRIMARY KEY (id_filial)
);

ALTER TABLE estudos.filial ADD CONSTRAINT endereco_filial_fk
FOREIGN KEY (id_endereco)
REFERENCES estudos.endereco (id_endereco)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE estudos.filial ADD CONSTRAINT unidade_filial_fk
FOREIGN KEY (id_unidade)
REFERENCES estudos.unidade (id_unidade)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;
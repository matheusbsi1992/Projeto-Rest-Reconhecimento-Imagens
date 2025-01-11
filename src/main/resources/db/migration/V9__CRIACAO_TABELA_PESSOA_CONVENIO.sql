--CREATE TABLE estudos.unidade_uniao_convenio (
--                id_unidade INTEGER NOT NULL,
--                id_convenio INTEGER NOT NULL,
--                CONSTRAINT unidade_convenio_pk PRIMARY KEY (id_unidade, id_convenio)
--);

--ALTER TABLE estudos.unidade_uniao_convenio ADD CONSTRAINT convenio_unidade_convenio_fk
--FOREIGN KEY (id_convenio)
--REFERENCES estudos.convenio (id_convenio)
--ON DELETE NO ACTION
--ON UPDATE NO ACTION
--NOT DEFERRABLE;


--ALTER TABLE estudos.unidade_uniao_convenio ADD CONSTRAINT unidade_unidade_convenio_fk
--FOREIGN KEY (id_unidade)
--REFERENCES estudos.unidade (id_unidade)
--ON DELETE NO ACTION
--ON UPDATE NO ACTION
--NOT DEFERRABLE;


--SELECT table_name
--FROM information_schema.tables
--WHERE table_schema = 'estudos';

CREATE TABLE estudos.pessoa_uniao_convenio (
    id_pessoa   CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
    --local_id    INT, -- ID da filial ou unidade
    id_convenio CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
    PRIMARY KEY (id_pessoa,id_convenio), --local_tipo, local_id, id_convenio),

    -- Referência à pessoa local
    FOREIGN KEY (id_pessoa)--, local_id)
        REFERENCES estudos.pessoa(id_pessoa),--, local_id),

    -- Referência ao convênio
    FOREIGN KEY (id_convenio)
        REFERENCES estudos.convenio(id_convenio)

    -- Restrições de consistência para garantir que o local_id esteja vinculado corretamente
    --CHECK (local_tipo IN ('filial', 'unidade'))
);




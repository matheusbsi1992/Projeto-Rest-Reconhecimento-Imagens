CREATE TABLE estudos.servico
(
  id_servico                SERIAL,
  nome_servico              VARCHAR,
  descricao_servico         VARCHAR,
  status_servico            Boolean,
  --data_cadastro_servico     TIMESTAMP,
  --data_atualizado_servico   TIMESTAMP
  CONSTRAINT servico_pk PRIMARY KEY (id_servico)
);

CREATE TABLE estudos.pessoa_uniao_servico
(
  id_pessoa  CHAR(26) NOT NULL,
  id_servico INTEGER NOT NULL,
  CONSTRAINT pessoa_servico_pk PRIMARY KEY (id_pessoa, id_servico),
  CONSTRAINT servico_pessoa_uniao_servico_fk FOREIGN KEY (id_servico)
      REFERENCES estudos.servico (id_servico) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT pessoa_pessoa_uniao_servico_fk FOREIGN KEY (id_pessoa)
      REFERENCES estudos.pessoa (id_pessoa) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
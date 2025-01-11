CREATE TABLE estudos.usuario_uniao_permissao (
                id_usuario   CHAR(26) NOT NULL,
                id_permissao INTEGER NOT NULL,
                CONSTRAINT usuario_uniao_permissao_pk PRIMARY KEY (id_usuario, id_permissao)
);

ALTER TABLE estudos.usuario_uniao_permissao ADD CONSTRAINT permissao_usuario_uniao_permissao_fk
FOREIGN KEY (id_permissao)
REFERENCES estudos.permissao (id_permissao)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE estudos.usuario_uniao_permissao ADD CONSTRAINT usuario_usuario_uniao_permissao_fk
FOREIGN KEY (id_usuario)
REFERENCES estudos.usuario (id_usuario)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

INSERT INTO estudos.usuario_uniao_permissao (id_usuario, id_permissao)
VALUES ('01JDW70TMC82P2XMBOTDR7MT3H', 1);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
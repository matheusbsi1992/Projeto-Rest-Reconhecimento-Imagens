CREATE TABLE estudos.usuario (
                id_usuario CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
                --id_unidade INTEGER,
                nome_usuario VARCHAR,
                usuario VARCHAR,
                senha_usuario VARCHAR,
                fone_usuario VARCHAR,
                account_non_expired_usuario BOOLEAN,
                account_non_locked_usuario BOOLEAN,
                credentials_non_expired_usuario BOOLEAN,
                status_usuario BOOLEAN,
                data_cadastro_usuario TIMESTAMP,
                data_atualizado_usuario TIMESTAMP,
                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);


--ALTER TABLE estudos.usuario ADD CONSTRAINT unidade_usuario_fk
--FOREIGN KEY (id_unidade)
--REFERENCES estudos.unidade (id_unidade)
--ON DELETE NO ACTION
--ON UPDATE NO ACTION
--NOT DEFERRABLE;


INSERT INTO estudos.usuario (
    id_usuario,
    --id_unidade,
    nome_usuario,
    usuario,
    senha_usuario,
    fone_usuario,
    status_usuario,
    account_non_expired_usuario,
    account_non_locked_usuario,
    credentials_non_expired_usuario,
    data_cadastro_usuario
)
VALUES (
    '01JDW70TMC82P2XMBOTDR7MT3H', -- id_usuario
    --1, -- id_unidade
    'MATHEUS ANDRADE', -- nome_usuario
    'matheusbsi1992@gmail.com', -- email_usuario
    '56d8576d02dc2d0b2b70ca1e2a1346593491222f74d27bf107b567133abb06ff59cfa0d4709496e879e8287a7c561ef21bc1486937b16be3f3339a7467b37d6e4c20cf020524191eaea7e905642d565fd03181eb100fad4f35fb5fc2cc5fa16515402dad1134a59692ecaa70ee264cf62fd71298173523fbbf21b25d72f0b8', -- senha_usuario
    '79999165475', -- fone_usuario
    true, -- status_usuario
    true, -- account_non_expired_usuario
    true, -- account_non_locked_usuario
    true,  -- credentials_non_expired_usuario
    NOW() -- data_criacao_usuario
);


SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
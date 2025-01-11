CREATE TABLE estudos.permissao (
                id_permissao   SERIAL,
                nome_permissao VARCHAR,
                CONSTRAINT permissao_pk PRIMARY KEY (id_permissao)
);

INSERT INTO estudos.permissao (nome_permissao)
VALUES  ('ROLE_ADMIN'),
        ('ROLE_USER'),
        ('ROLE_CUSTOMER'),
        ('ROLE_PROFESSIONAL');

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
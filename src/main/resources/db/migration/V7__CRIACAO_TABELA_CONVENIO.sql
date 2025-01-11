CREATE TABLE estudos.convenio (
                id_convenio CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
                id_endereco INTEGER,
                nome_fantasia_convenio VARCHAR,
                razao_social_convenio VARCHAR,
                cnpj_convenio VARCHAR,
                email_convenio VARCHAR,
                telefone_convenio VARCHAR,
                setor_convenio VARCHAR,
                data_cadastro_convenio TIMESTAMP,
                data_atualizado_convenio TIMESTAMP,
                CONSTRAINT convenio_pk PRIMARY KEY (id_convenio)
);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';

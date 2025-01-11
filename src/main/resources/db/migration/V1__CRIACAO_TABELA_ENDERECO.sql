CREATE TABLE estudos.endereco (
                id_endereco SERIAL,
                logradouro_endereco VARCHAR,
                numero_endereco VARCHAR,
                complemento_endereco VARCHAR,
                bairro_endereco VARCHAR,
                cidade_endereco VARCHAR,
                estado_endereco VARCHAR(2),
                cep_endereco VARCHAR,
                CONSTRAINT endereco_pk PRIMARY KEY (id_endereco)
);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
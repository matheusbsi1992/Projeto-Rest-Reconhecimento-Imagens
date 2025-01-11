CREATE TABLE estudos.unidade (
                id_unidade CHAR(26) DEFAULT estudos.generate_ulid() NOT NULL,
                id_endereco INTEGER,
                razao_social_unidade VARCHAR,
                nome_fantasia_unidade VARCHAR,
                documento_unidade VARCHAR,
                email_unidade VARCHAR,
                telefone_unidade VARCHAR,
                setor_unidade VARCHAR,
                status_unidade Boolean,
                data_cadastro_unidade TIMESTAMP,
                data_atualizado_unidade TIMESTAMP,
                CONSTRAINT unidade_pk PRIMARY KEY (id_unidade)
);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';

--INSERT INTO estudos.unidade (
--    id_unidade,
--    razao_social_unidade,
--    nome_fantasia_unidade,
--    documento_unidade,
--    email_unidade,
--    telefone_unidade,
--    setor_unidade,
--    status_unidade,
--    data_cadastro_unidade
--)
--VALUES (
  --  '07D45UNTA648329YZFOGOHYVGW' -- id_unidade
  --  'master', -- razao_social_unidade
  --  'master', -- nome_fantasia_unidade
  -- '12345678901234', -- documento_unidade (Exemplo de CNPJ)
  --  'matheusbsi1992@gmail.com', -- email_unidade
  --  '79999123456', -- telefone_unidade
  --  'Setor Exemplo', -- setor_unidade
  --  true, -- status_unidade
  --  NOW() -- data_cadastro_unidade
--);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
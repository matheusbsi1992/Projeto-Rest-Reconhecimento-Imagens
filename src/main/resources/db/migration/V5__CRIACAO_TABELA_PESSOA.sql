--CREATE TABLE pessoa_local (
--    id_usuario INT NOT NULL,
--    id_sub_unidade INT,
--    id_unidade INT,
--    tipo_pessoa VARCHAR,
--    PRIMARY KEY (id_usuario, id_sub_unidade, id_unidade),
--    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
--    FOREIGN KEY (id_sub_unidade) REFERENCES subunidade(id_sub_unidade),
--    FOREIGN KEY (id_unidade) REFERENCES unidade(id_unidade)
--);
CREATE TABLE estudos.pessoa (
    id_pessoa                       CHAR(26) DEFAULT estudos.generate_ulid() ,            -- ID da PESSOA,
    id_unidade                      CHAR(26) DEFAULT estudos.generate_ulid() ,            -- ID da UNIDADE
    id_filial                       CHAR(26) DEFAULT estudos.generate_ulid() ,            -- ID da FILIAL
    id_pessoa_unidade_principal     CHAR(26) DEFAULT estudos.generate_ulid() ,            -- ID RESPECTIVO DA PESSOA
    local_tipo                      VARCHAR,                                              -- 'FILIAL' ou 'UNIDADE'
    PRIMARY KEY (id_pessoa),--local_id),
    FOREIGN KEY (id_pessoa) REFERENCES estudos.usuario(id_usuario),  --  administrador|paciente|usuario|cliente|profissional
    -- Chave estrangeira condicional dependendo do tipo de local (filial/unidade)
    --CHECK (local_tipo IN ('FILIAL', 'UNIDADE')),
    -- Se for 'filial', garantir que o local_id exista em filial
    FOREIGN KEY (id_unidade) REFERENCES estudos.unidade(id_unidade),
    -- Se for 'unidade', garantir que o local_id exista em unidade
    FOREIGN KEY (id_filial)  REFERENCES estudos.filial(id_filial)
);
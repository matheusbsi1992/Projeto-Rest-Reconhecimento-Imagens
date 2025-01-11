CREATE TABLE estudos.paciente (
                id_usuario_paciente CHAR(26) DEFAULT estudos.generate_ulid(),
                id_pessoa           CHAR(26) NOT NULL,
                --nome_paciente       VARCHAR,
                sexo_paciente       VARCHAR,
                data_nascimento_paciente DATE,
                rg_paciente VARCHAR,
                cpf_paciente VARCHAR,
                altura_paciente DOUBLE PRECISION,
                peso_paciente DOUBLE PRECISION,
                nome_mae_paciente VARCHAR,
                profissao_paciente VARCHAR,
                cargo_paciente VARCHAR,
                setor_paciente VARCHAR,
                numero_cartao_sus_paciente VARCHAR,
                status_paciente Boolean,

                CONSTRAINT paciente_pk PRIMARY KEY (id_usuario_paciente)
);
--COMMENT ON TABLE estudos.paciente IS 'pessoa responsavel por acessar o sistema e visualizar os seus exames.
--Sera preciso gerar a nota fiscal do usuario ou nao ?
--paciente vinculado sera indicada as varias unidades e/ou filiais';

ALTER TABLE estudos.paciente ADD CONSTRAINT usuario_paciente_fk
FOREIGN KEY (id_usuario_paciente)
REFERENCES estudos.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE estudos.paciente ADD CONSTRAINT pessoa_paciente_fk
FOREIGN KEY (id_pessoa)
REFERENCES estudos.pessoa (id_pessoa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
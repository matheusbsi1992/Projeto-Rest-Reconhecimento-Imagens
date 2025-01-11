ALTER TABLE estudos.unidade ADD CONSTRAINT endereco_unidade_fk
FOREIGN KEY (id_endereco)
REFERENCES estudos.endereco (id_endereco)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';
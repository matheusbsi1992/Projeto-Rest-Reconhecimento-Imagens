ALTER TABLE estudos.convenio ADD CONSTRAINT endereco_convenio_fk
FOREIGN KEY (id_endereco)
REFERENCES estudos.endereco (id_endereco)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'estudos';


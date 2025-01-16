ALTER TABLE perguntas DROP COLUMN idioma;
ALTER TABLE perguntas ADD COLUMN idioma_origem VARCHAR(50) NOT NULL;
ALTER TABLE perguntas ADD COLUMN idioma_destino VARCHAR(50) NOT NULL;perguntas
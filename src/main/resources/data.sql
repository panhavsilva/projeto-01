INSERT INTO papel (id, nome)
VALUES
    (1, 'ADMIN'),
    (2, 'PEDAGOGICO'),
    (3, 'RECRUITER'),
    (4, 'PROFESSOR'),
    (5, 'ALUNO')
ON CONFLICT (id) DO NOTHING;

INSERT INTO usuario (id, nome_usuario, senha, id_papel)
VALUES (1, 'dummy', '$2a$10$R8dSgqpWIu.BwmV42pwI.O6a3dHdb1IlQplFnRGiNIPj8hitI6CNy', 1)
ON CONFLICT (id) DO NOTHING;
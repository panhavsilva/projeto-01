INSERT INTO papel (id, nome)
VALUES (1, 'ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO usuario (id, nome_usuario, senha, papel_id)
VALUES (1, 'dummy', '$2a$10$R8dSgqpWIu.BwmV42pwI.O6a3dHdb1IlQplFnRGiNIPj8hitI6CNy', 1)
ON CONFLICT (id) DO NOTHING;
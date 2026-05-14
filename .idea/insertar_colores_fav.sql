

INSERT INTO ColorFavorito (idUsuario, idColor)
SELECT u.id, c.id
FROM Usuario u, Color c
WHERE u.name = 'Ariel'
  AND c.name IN ('negro', 'azul', 'amarillo');

-- Verificar resultado:
-- SELECT u.name, c.name as color
-- FROM ColorFavorito cf
-- JOIN Usuario u ON cf.idUsuario = u.id
-- JOIN Color c ON cf.idColor = c.id
-- ORDER BY u.name;
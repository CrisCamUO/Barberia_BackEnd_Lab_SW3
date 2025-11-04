INSERT INTO categorias (id, nombreCategoria) VALUES (1, 'Corte');
INSERT INTO categorias (id, nombreCategoria) VALUES (2, 'Barba');
INSERT INTO categorias (id, nombreCategoria) VALUES (3, 'Tinte');

INSERT INTO productos (nombre, descripcion,estado, precio, imagen, idCategoria) VALUES 
('Corte Sencillo', 'descripcion 1',TRUE, 10000, 'uploads\bf20e6649085b7e8550f2260fa75e00a.jpg', 1);
INSERT INTO productos (nombre, descripcion,estado, precio, imagen, idCategoria) VALUES
('Barba Completa', 'descripcion 2',FALSE, 7000, 'uploads\281f3c2ff3a5da07575482acdeed878e.jpg', 2);
INSERT INTO productos (nombre, descripcion,estado, precio, imagen, idCategoria) VALUES
('Tinte Rojo', 'descripcion 3',TRUE, 20000, 'uploads\maxybelt-6-46-rojo-shangai-02-2025.jpg', 3);


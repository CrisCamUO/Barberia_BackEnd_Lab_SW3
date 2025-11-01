INSERT INTO categorias (id, nombreCategoria) VALUES (1, 'Corte');
INSERT INTO categorias (id, nombreCategoria) VALUES (2, 'Barba');
INSERT INTO categorias (id, nombreCategoria) VALUES (3, 'Tinte');

INSERT INTO productos (nombre, descripcion,estado, precio, imagen, idCategoria) VALUES 
('Corte Sencillo', 'descripcion 1',TRUE, 10000, 'ruta:imagen1.jpg', 1);
INSERT INTO productos (nombre, descripcion,estado, precio, imagen, idCategoria) VALUES
('Barba Completa', 'descripcion 2',FALSE, 7000, 'ruta:imagen2.jpg', 2);
INSERT INTO productos (nombre, descripcion,estado, precio, imagen, idCategoria) VALUES
('Tinte Rojo', 'descripcion 3',TRUE, 20000, 'ruta:imagen3.jpg', 3);


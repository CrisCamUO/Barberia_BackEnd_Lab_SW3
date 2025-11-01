CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(255)
);


CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion VARCHAR(500),
    estado BOOLEAN, -- true = Disponible, false = No Disponible
    precio DOUBLE,
    imagen VARCHAR(255), -- Ruta o nombre del archivo de imagen
    idCategoria INT,
    FOREIGN KEY (idCategoria) REFERENCES categorias(id)
);









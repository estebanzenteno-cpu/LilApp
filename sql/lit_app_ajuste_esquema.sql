-- Ajuste del esquema de la base lit_app para que coincida con las entidades JPA.
-- Solo AGREGA lo que falta; no modifica ni borra datos existentes.
-- Ejecutar una sola vez: la columna idRol de Usuario no admite IF NOT EXISTS.
-- Requisito: la tabla Usuario debe estar vacia (la columna idRol es NOT NULL).

USE lit_app;

CREATE TABLE IF NOT EXISTS Rol (
    idRol INT NOT NULL AUTO_INCREMENT,
    nombreRol VARCHAR(50) NOT NULL,
    PRIMARY KEY (idRol),
    UNIQUE KEY uk_rol_nombre (nombreRol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS TipoProducto (
    idTipodeProducto INT NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(100) NOT NULL,
    PRIMARY KEY (idTipodeProducto),
    UNIQUE KEY uk_tipoproducto_nombre (nombreProducto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de unión Proveedor <-> TipoProducto (Proveedor.tiposProducto).
CREATE TABLE IF NOT EXISTS ofrece (
    idProveedor INT NOT NULL,
    idTipodeProducto INT NOT NULL,
    PRIMARY KEY (idProveedor, idTipodeProducto),
    CONSTRAINT fk_ofrece_proveedor FOREIGN KEY (idProveedor) REFERENCES Proveedor (idProveedor),
    CONSTRAINT fk_ofrece_tipoproducto FOREIGN KEY (idTipodeProducto) REFERENCES TipoProducto (idTipodeProducto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Relacion Usuario -> Rol (Usuario.rol). Usuario esta vacia, por eso la columna NOT NULL se agrega sin valor por defecto.
ALTER TABLE Usuario
    ADD COLUMN idRol INT NOT NULL,
    ADD CONSTRAINT fk_usuario_rol FOREIGN KEY (idRol) REFERENCES Rol (idRol);

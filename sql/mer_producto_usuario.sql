-- Ejecutar una vez sobre el esquema seleccionado en Workbench, con las seis
-- tablas base (cliente, proveedor, usuario, rol, tipoproducto y ofrece).
-- En instalaciones donde ya se aplicaron las tablas del MER, ejecutar
-- solamente la normalizacion de la columna al final, si corresponde.

CREATE TABLE producto (
 idProducto INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100), tipoProducto VARCHAR(100),
 precio DECIMAL(10,2)
) ENGINE=InnoDB;
ALTER TABLE usuario ADD COLUMN idProducto INT NULL, ADD CONSTRAINT fk_usuario_producto FOREIGN KEY (idProducto) REFERENCES producto(idProducto);
CREATE TABLE cotizacion (
 idCotizacion INT AUTO_INCREMENT PRIMARY KEY, fecha DATE, precio DECIMAL(10,2),
 estatus ENUM('activo','inactivo') DEFAULT 'activo', idCliente INT,
 FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)
) ENGINE=InnoDB;
CREATE TABLE pago (
 idPago INT AUTO_INCREMENT PRIMARY KEY, monto DECIMAL(10,2), tipoPago VARCHAR(50), fecha DATE
) ENGINE=InnoDB;
CREATE TABLE pedido (
 idPedido INT AUTO_INCREMENT PRIMARY KEY, fechaRegistro DATE, tipoTrabajo VARCHAR(100),
 recibo VARCHAR(255), estatus ENUM('activo','inactivo') DEFAULT 'activo', idCliente INT, idPago INT,
 FOREIGN KEY (idCliente) REFERENCES cliente(idCliente),
 FOREIGN KEY (idPago) REFERENCES pago(idPago)
) ENGINE=InnoDB;
CREATE TABLE archivo (
 idArchivo INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255), fecha DATE, descripcion TEXT,
 archivo_cdr LONGBLOB, idCliente INT UNIQUE, idUsuario INT UNIQUE,
 FOREIGN KEY (idCliente) REFERENCES cliente(idCliente),
 FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
) ENGINE=InnoDB;
CREATE TABLE cotizacion_producto (
 idCotizacion INT NOT NULL, idProducto INT NOT NULL, PRIMARY KEY (idCotizacion,idProducto),
 FOREIGN KEY (idCotizacion) REFERENCES cotizacion(idCotizacion),
 FOREIGN KEY (idProducto) REFERENCES producto(idProducto)
) ENGINE=InnoDB;
CREATE TABLE pedido_producto (
 idPedido INT NOT NULL, idProducto INT NOT NULL, PRIMARY KEY (idPedido,idProducto),
 FOREIGN KEY (idPedido) REFERENCES pedido(idPedido),
 FOREIGN KEY (idProducto) REFERENCES producto(idProducto)
) ENGINE=InnoDB;


-- La aplicacion utiliza contrasena. RENAME COLUMN conserva los datos.
SET @normalizar_contrasena = IF(
    EXISTS (SELECT 1 FROM information_schema.COLUMNS
            WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'usuario'
              AND CAST(COLUMN_NAME AS BINARY) = CAST('contraseña' AS BINARY))
    AND NOT EXISTS (SELECT 1 FROM information_schema.COLUMNS
                    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'usuario'
                      AND CAST(COLUMN_NAME AS BINARY) = CAST('contrasena' AS BINARY)),
    'ALTER TABLE usuario RENAME COLUMN `contraseña` TO contrasena',
    'SELECT 1'
);
PREPARE normalizar_contrasena FROM @normalizar_contrasena;
EXECUTE normalizar_contrasena;
DEALLOCATE PREPARE normalizar_contrasena;

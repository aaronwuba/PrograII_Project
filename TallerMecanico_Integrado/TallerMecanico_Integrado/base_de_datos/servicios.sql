-- Para probar el catálogo por separado. Si el grupo ya tiene base de datos,
-- use solamente la tabla y ajuste los nombres para coincidir con su proyecto.
CREATE DATABASE IF NOT EXISTS taller_mecanico;
USE taller_mecanico;

CREATE TABLE IF NOT EXISTS servicios (
    idServicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);

INSERT INTO servicios (nombre, descripcion, precio) VALUES
('Cambio de aceite', 'Cambio de aceite y revisión del filtro', 15000),
('Mantenimiento preventivo', 'Revisión general para prevenir fallas', 25000),
('Revisión de frenos', 'Inspección del sistema de frenos', 20000),
('Diagnóstico general', 'Revisión para identificar fallas', 10000);


/*
========================================================
 SISTEMA DE BIBLIOTECA - SQL SERVER
========================================================

Características:
- Elimina la base de datos si ya existe
- Cierra conexiones activas
- Recrea la base de datos limpia
- Crea tablas relacionales
- Define PRIMARY KEY, FOREIGN KEY,
  UNIQUE, CHECK y DEFAULT
- Orden correcto para evitar conflictos
- Compatible con SQL Server
========================================================
*/

--------------------------------------------------------
-- 1. ELIMINAR BASE DE DATOS SI EXISTE
--------------------------------------------------------

USE master;
GO

IF EXISTS (
    SELECT name
    FROM sys.databases
    WHERE name = 'BibliotecaDB'
)
BEGIN

    -- Cerrar conexiones activas
    ALTER DATABASE BibliotecaDB
    SET SINGLE_USER
    WITH ROLLBACK IMMEDIATE;

    -- Eliminar base de datos
    DROP DATABASE BibliotecaDB;
END
GO

--------------------------------------------------------
-- 2. CREAR BASE DE DATOS
--------------------------------------------------------

CREATE DATABASE BibliotecaDB;
GO

USE BibliotecaDB;
GO

--------------------------------------------------------
-- 3. TABLA: CATEGORIA
--------------------------------------------------------

CREATE TABLE Categoria (
    id_categoria INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);
GO

--------------------------------------------------------
-- 4. TABLA: LIBRO
--------------------------------------------------------

CREATE TABLE Libro (
    id_libro INT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    anio_publicacion INT,
    stock INT NOT NULL DEFAULT 0,
    precio DECIMAL(10,2) NOT NULL,

    id_categoria INT NOT NULL,

    CONSTRAINT FK_Libro_Categoria
        FOREIGN KEY (id_categoria)
        REFERENCES Categoria(id_categoria),

    CONSTRAINT CHK_Libro_Stock
        CHECK (stock >= 0),

    CONSTRAINT CHK_Libro_Precio
        CHECK (precio >= 0)
);
GO

--------------------------------------------------------
-- 5. TABLA: CLIENTE
--------------------------------------------------------

CREATE TABLE Cliente (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(150) UNIQUE,
    direccion VARCHAR(255),
    fecha_registro DATETIME DEFAULT GETDATE()
);
GO

--------------------------------------------------------
-- 6. TABLA: EMPLEADO
--------------------------------------------------------

CREATE TABLE Empleado (
    id_empleado INT IDENTITY(1,1) PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(150) UNIQUE,
    salario DECIMAL(10,2) NOT NULL,

    CONSTRAINT CHK_Empleado_Salario
        CHECK (salario >= 0)
);
GO

--------------------------------------------------------
-- 7. TABLA: ADMINISTRADOR
--------------------------------------------------------

CREATE TABLE Administrador (
    id_administrador INT IDENTITY(1,1) PRIMARY KEY,

    id_empleado INT NOT NULL UNIQUE,

    usuario VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,

    CONSTRAINT FK_Administrador_Empleado
        FOREIGN KEY (id_empleado)
        REFERENCES Empleado(id_empleado)
        ON DELETE CASCADE
);
GO

--------------------------------------------------------
-- 8. TABLA: VENTA
--------------------------------------------------------

CREATE TABLE Venta (
    id_venta INT IDENTITY(1,1) PRIMARY KEY,

    fecha_venta DATETIME NOT NULL DEFAULT GETDATE(),

    id_cliente INT NOT NULL,
    id_empleado INT NOT NULL,

    total DECIMAL(10,2) NOT NULL DEFAULT 0,

    CONSTRAINT FK_Venta_Cliente
        FOREIGN KEY (id_cliente)
        REFERENCES Cliente(id_cliente),

    CONSTRAINT FK_Venta_Empleado
        FOREIGN KEY (id_empleado)
        REFERENCES Empleado(id_empleado),

    CONSTRAINT CHK_Venta_Total
        CHECK (total >= 0)
);
GO

--------------------------------------------------------
-- 9. TABLA: DETALLE_VENTA
--------------------------------------------------------

CREATE TABLE Detalle_Venta (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,

    id_venta INT NOT NULL,
    id_libro INT NOT NULL,

    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,

    CONSTRAINT FK_DetalleVenta_Venta
        FOREIGN KEY (id_venta)
        REFERENCES Venta(id_venta)
        ON DELETE CASCADE,

    CONSTRAINT FK_DetalleVenta_Libro
        FOREIGN KEY (id_libro)
        REFERENCES Libro(id_libro),

    CONSTRAINT CHK_DetalleVenta_Cantidad
        CHECK (cantidad > 0),

    CONSTRAINT CHK_DetalleVenta_Precio
        CHECK (precio_unitario >= 0),

    CONSTRAINT CHK_DetalleVenta_Subtotal
        CHECK (subtotal >= 0)
);
GO

--------------------------------------------------------
-- 10. DATOS DE PRUEBA
--------------------------------------------------------

INSERT INTO Categoria(nombre, descripcion)
VALUES
('Programación', 'Libros de desarrollo de software'),
('Base de Datos', 'Libros de SQL y modelado'),
('Redes', 'Infraestructura y networking');
GO

INSERT INTO Libro(
    titulo,
    autor,
    isbn,
    anio_publicacion,
    stock,
    precio,
    id_categoria
)
VALUES
(
    'Clean Code',
    'Robert C. Martin',
    '9780132350884',
    2008,
    10,
    120.50,
    1
),
(
    'Database System Concepts',
    'Silberschatz',
    '9780073523323',
    2019,
    5,
    180.00,
    2
);
GO

--------------------------------------------------------
-- 11. CONSULTA DE VALIDACION
--------------------------------------------------------

SELECT
    l.id_libro,
    l.titulo,
    l.autor,
    c.nombre AS categoria
FROM Libro l
INNER JOIN Categoria c
    ON l.id_categoria = c.id_categoria;
GO

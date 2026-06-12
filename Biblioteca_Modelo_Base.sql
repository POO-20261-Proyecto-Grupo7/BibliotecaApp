
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
-- ELIMINAR BASE DE DATOS SI EXISTE
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
-- CREAR BASE DE DATOS
--------------------------------------------------------

CREATE DATABASE BibliotecaDB;
GO

USE BibliotecaDB;
GO

--------------------------------------------------------
-- TABLA: CATEGORIA
--------------------------------------------------------

CREATE TABLE Categoria (
    id_categoria INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);
GO

--------------------------------------------------------
-- TABLA: EDITORIAL
--------------------------------------------------------

CREATE TABLE Editorial(
    id_editorial INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(150) UNIQUE NOT NULL
);
GO

--------------------------------------------------------
-- TABLA: AUTOR
--------------------------------------------------------

CREATE TABLE Autor(
    id_autor INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(150) UNIQUE NOT NULL
);
GO

--------------------------------------------------------
-- TABLA: ETIQUETA
--------------------------------------------------------

CREATE TABLE Etiqueta(
    id_etiqueta INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
);
GO

--------------------------------------------------------
-- TABLA: LIBRO
--------------------------------------------------------

CREATE TABLE Libro (
    id_libro INT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    -- autor VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    anio_publicacion INT,
    stock INT NOT NULL DEFAULT 0,
    precio DECIMAL(10,2) NOT NULL,

    -- id_categoria INT NOT NULL,

    sinopsis VARCHAR(MAX),

    id_editorial INT,

    --CONSTRAINT FK_Libro_Categoria
    --    FOREIGN KEY (id_categoria)
    --    REFERENCES Categoria(id_categoria),

    CONSTRAINT FK_Libro_Editorial
        FOREIGN KEY(id_editorial)
        REFERENCES Editorial(id_editorial),

    CONSTRAINT CHK_Libro_Stock
        CHECK (stock >= 0),

    CONSTRAINT CHK_Libro_Precio
        CHECK (precio >= 0)
);
GO

--------------------------------------------------------
-- TABLA: LIBRO_AUTOR
--------------------------------------------------------

CREATE TABLE Libro_Autor(
    id_libro INT,
    id_autor INT,

    PRIMARY KEY(id_libro,id_autor),

    CONSTRAINT FK_LibroAutor_Libro
        FOREIGN KEY(id_libro)
        REFERENCES Libro(id_libro),

    CONSTRAINT FK_LibroAutor_Autor
        FOREIGN KEY(id_autor)
        REFERENCES Autor(id_autor)
);
GO

--------------------------------------------------------
-- TABLA: LIBRO_ETIQUETA
--------------------------------------------------------

CREATE TABLE Libro_Etiqueta(
    id_libro INT,
    id_etiqueta INT,

    PRIMARY KEY(id_libro,id_etiqueta),
    
    CONSTRAINT FK_LibroEtiqueta_Libro
        FOREIGN KEY(id_libro)
        REFERENCES Libro(id_libro),
    
    CONSTRAINT FK_LibroEtiqueta_Etiqueta
        FOREIGN KEY(id_etiqueta)
        REFERENCES Etiqueta(id_etiqueta)
);

--------------------------------------------------------
-- TABLA: LIBRO_CATEGORIA
--------------------------------------------------------

CREATE TABLE Libro_Categoria(
    id_libro INT,
    id_categoria INT,

    PRIMARY KEY(id_libro,id_categoria),
    
    CONSTRAINT FK_LibroCategoria_Libro
        FOREIGN KEY(id_libro)
        REFERENCES Libro(id_libro),
    
    CONSTRAINT FK_LibroCategoria_Categoria
        FOREIGN KEY(id_categoria)
        REFERENCES Categoria(id_categoria)
);

CREATE TABLE Ubicacion(
    id_ubicacion INT IDENTITY PRIMARY KEY,

    sede VARCHAR(100) NOT NULL,

    pasillo VARCHAR(50) NOT NULL,

    estante VARCHAR(50) NOT NULL,

    nivel VARCHAR(50) NOT NULL,

    CONSTRAINT UQ_Ubicacion
        UNIQUE(
            sede,
            pasillo,
            estante,
            nivel
        )
);
GO

CREATE TABLE Ejemplar(
    id_ejemplar INT IDENTITY PRIMARY KEY,

    id_libro INT NOT NULL,

    id_ubicacion INT,

    codigo VARCHAR(50) UNIQUE NOT NULL,

    estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE',

    CONSTRAINT FK_Ejemplar_Libro
        FOREIGN KEY(id_libro)
        REFERENCES Libro(id_libro),

    CONSTRAINT CHK_Ejemplar_Estado
        CHECK (
            estado IN (
                'DISPONIBLE',
                'PRESTADO',
                'PERDIDO',
                'DANIADO'
            )
        ),

    CONSTRAINT FK_Ejemplar_Ubicacion
        FOREIGN KEY(id_ubicacion)
        REFERENCES Ubicacion(id_ubicacion)
);

--------------------------------------------------------
-- TABLA: CLIENTE
--------------------------------------------------------

CREATE TABLE Socio (
    id_socio INT IDENTITY(1,1) PRIMARY KEY,
    
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,

    dni VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(150) UNIQUE,
    direccion VARCHAR(255),
    fecha_registro DATETIME DEFAULT GETDATE(),

    habilitado BIT NOT NULL DEFAULT 1
);
GO

CREATE TABLE Prestamo(
    id_prestamo INT IDENTITY PRIMARY KEY,

    id_socio INT NOT NULL,

    fecha_prestamo DATETIME NOT NULL DEFAULT GETDATE(),

    fecha_limite DATETIME NOT NULL,

    fecha_devolucion DATETIME NULL,

    estado VARCHAR(20) NOT NULL,

    CONSTRAINT FK_Prestamo_Socio
        FOREIGN KEY(id_socio)
        REFERENCES Socio(id_socio)
);
GO

CREATE TABLE Prestamo_Ejemplar(
    id_prestamo INT NOT NULL,
    id_ejemplar INT NOT NULL,

    PRIMARY KEY(id_prestamo,id_ejemplar),

    CONSTRAINT FK_PE_Prestamo
        FOREIGN KEY(id_prestamo)
        REFERENCES Prestamo(id_prestamo),

    CONSTRAINT FK_PE_Ejemplar
        FOREIGN KEY(id_ejemplar)
        REFERENCES Ejemplar(id_ejemplar)
);
GO

CREATE TABLE RenovacionPrestamo(
    id_renovacion INT IDENTITY PRIMARY KEY,

    id_prestamo INT NOT NULL,

    fecha_renovacion DATETIME NOT NULL DEFAULT GETDATE(),

    fecha_limite_anterior DATETIME NOT NULL,

    fecha_limite_nueva DATETIME NOT NULL,

    token VARCHAR(200) NOT NULL UNIQUE,

    utilizado BIT NOT NULL DEFAULT 0,

    fecha_utilizacion DATETIME NULL,

    CONSTRAINT FK_RenovacionPrestamo_Prestamo
        FOREIGN KEY(id_prestamo)
        REFERENCES Prestamo(id_prestamo)
);
GO

CREATE TABLE NotificacionPrestamo(
    id_notificacion INT IDENTITY PRIMARY KEY,

    id_prestamo INT NOT NULL,

    canal VARCHAR(20) NOT NULL,

    fecha_envio DATETIME NOT NULL,

    exito BIT NOT NULL,

    CONSTRAINT FK_NotificacionPrestamo_Prestamo
        FOREIGN KEY(id_prestamo)
        REFERENCES Prestamo(id_prestamo)
);
GO

--------------------------------------------------------
-- TABLA: EMPLEADO
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
-- TABLA: ADMINISTRADOR
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
-- TABLA: VENTA
--------------------------------------------------------

CREATE TABLE Venta (
    id_venta INT IDENTITY(1,1) PRIMARY KEY,

    fecha_venta DATETIME NOT NULL DEFAULT GETDATE(),

    id_socio INT NOT NULL,
    id_empleado INT NOT NULL,

    total DECIMAL(10,2) NOT NULL DEFAULT 0,

    CONSTRAINT FK_Venta_Socio
        FOREIGN KEY (id_socio)
        REFERENCES Socio(id_socio),

    CONSTRAINT FK_Venta_Empleado
        FOREIGN KEY (id_empleado)
        REFERENCES Empleado(id_empleado),

    CONSTRAINT CHK_Venta_Total
        CHECK (total >= 0)
);
GO

--------------------------------------------------------
-- TABLA: DETALLE_VENTA
--------------------------------------------------------

CREATE TABLE DetalleVenta (
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
-- DATOS DE PRUEBA
--------------------------------------------------------

INSERT INTO Categoria(nombre, descripcion)
VALUES
('Programación', 'Libros de desarrollo de software'),
('Base de Datos', 'Libros de SQL y modelado'),
('Redes', 'Infraestructura y networking');
GO

INSERT INTO Editorial(nombre)
VALUES
('Editorial 1'),
('Editorial 2');
GO


INSERT INTO libro(
    titulo,
    isbn,
    anio_publicacion,
    stock,
    precio,
    sinopsis,
    id_editorial
)
VALUES
(
    'Clean Code',
    -- 'Robert C. Martin',
    '9780132350884',
    2008,
    10,
    120.50,
    '',
    1
),
(
    'Database System Concepts',
    -- 'Silberschatz',
    '9780073523323',
    2019,
    5,
    180.00,
    '',
    2
);
GO

INSERT INTO Ejemplar (
    id_libro,
    codigo,
    estado
)
VALUES
-- Clean Code
(1, 'CC-0001', 'DISPONIBLE'),
(1, 'CC-0002', 'DISPONIBLE'),
(1, 'CC-0003', 'DISPONIBLE'),
(1, 'CC-0004', 'DISPONIBLE'),
(1, 'CC-0005', 'PRESTADO'),

-- Database System Concepts
(2, 'DBSC-0001', 'DISPONIBLE'),
(2, 'DBSC-0002', 'DISPONIBLE'),
(2, 'DBSC-0003', 'PRESTADO'),
(2, 'DBSC-0004', 'DISPONIBLE'),
(2, 'DBSC-0005', 'DISPONIBLE');
GO

--------------------------------------------------------
-- DATOS DE PRUEBA: SOCIOS
--------------------------------------------------------

INSERT INTO Socio (
    nombres,
    apellidos,
    dni,
    telefono,
    correo,
    direccion,
    habilitado
)
VALUES
(
    'Juan Carlos',
    'Perez Gomez',
    '12345678',
    '999111222',
    'juan.perez@correo.com',
    'Av. Lima 123',
    1
),
(
    'Maria Elena',
    'Lopez Torres',
    '87654321',
    '999333444',
    'maria.lopez@correo.com',
    'Jr. Arequipa 456',
    1
),
(
    'Pedro',
    'Ramirez Diaz',
    '11223344',
    '999555666',
    'pedro.ramirez@correo.com',
    'Av. Universitaria 789',
    0
);
GO

--------------------------------------------------------
-- CONSULTA DE VALIDACION
--------------------------------------------------------

SELECT
    l.id_libro,
    l.titulo
FROM Libro l;
GO

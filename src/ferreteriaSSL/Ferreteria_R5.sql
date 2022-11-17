CREATE SCHEMA IF NOT exists RETO5;
USE RETO5;

CREATE TABLE Categoria (
ID_Categoria VARCHAR(15) PRIMARY KEY NOT NULL,
descripcion VARCHAR(100)
);

CREATE TABLE  Producto (
ID_Producto INT NOT NULL AUTO_INCREMENT, 
Nombre VARCHAR(100) NOT NULL, 
Valor_Venta  FLOAT NOT NULL, 
Costo FLOAT NOT NULL, 
Cantidad INT NOT NULL,
ID_Categoria VARCHAR(15) NOT NULL, 
PRIMARY KEY (ID_Producto),
FOREIGN KEY (ID_Categoria) REFERENCES Categoria (ID_Categoria)
);

--
INSERT INTO Categoria(ID_Categoria, descripcion) VALUES ("Fijacion","Tornillos,clavos,grapas");
INSERT INTO Categoria(ID_Categoria, descripcion) VALUES ("Fontaneria","Tubos,codos,uniones");
INSERT INTO Categoria(ID_Categoria, descripcion) VALUES ("Herramienta","Llaves,destornilladores");
INSERT INTO Categoria(ID_Categoria, descripcion) VALUES ("Cerrajeria","modelos,cerraduras,llaveros");
INSERT INTO Categoria(ID_Categoria, descripcion) VALUES ("Proteccion","Guantes,gafas,tapones de oidos");

--
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Clavos", 100,500,5,"Fijacion");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Arandelas", 915,1830,2,"Fijacion");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Tubo", 5067,30402,6,"Fontaneria");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Llaves inglesas", 5881,17643,3,"Herramienta");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Martillo", 2905,26145,9,"Herramienta");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Guantes de trabajo", 6082,12164,2,"Proteccion");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Balde", 8303,49818,6,"Fontaneria");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Pegamento", 5543,38801,7,"Fijacion");
INSERT INTO Producto (Nombre, Valor_Venta, Costo, Cantidad, ID_Categoria) VALUES ("Cerradura", 356789,356789,1,"Cerrajeria");



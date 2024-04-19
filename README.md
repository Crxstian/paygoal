# API de Gestión de Productos
Ejercicio Jr - Java - Cristian Siles   
Esta API permite gestionar productos con las siguientes operaciones: crear, modificar, buscar por ID o nombre, listar por precio y eliminar.

Versiones Utilizadas
Spring Framework: 3.2.5
Java: 17
H2 Database: H2 2.2.224 (2023-09-17)
Configuración de Spring
Si necesitas configurar Spring, puedes usar las siguientes propiedades en tu archivo application.properties:

spring.application.name=paygoal
spring.h2.console.enabled=true
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:~/paygoal
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.main.allow-bean-definition-overriding=true

Tambien utilizo DTOs -ProductoDto (Data Transfer Objects) para enviar datos entre la capa de presentación y la capa de servicio en la aplicación,para separar la representación de los datos de su estructura interna.

Endpoints
Listar Productos por Precio
GET /productos
Devuelve la lista de productos ordenados por precio de forma descendente.

Agregar Producto
POST /productos
Agrega un nuevo producto.

Body Request:
{
  "nombre": "Calculadora",
  "descripcion": "Calculadora CASIO fx-95",
  "precio": 200000,
  "cantidad": 38
}
Modificar Producto
bash
Copy code
PUT /productos
Modifica un producto existente, se necesita enviar el objeto producto completo, y modificar alguno de sus atributos
Body Request:
{
    "id": 1,
    "nombre": "cable usb extension",
    "descripcion": "2 metros ",
    "precio": 30000.0,
    "cantidad": 30
}
Buscar Producto por ID o Nombre
GET /productos/{idONombre}
Busca un producto por ID o nombre, el parametro puede ser un numero para buscar por id o un texto con el que buscara por nombre,
el sistema no trae solo un resultado, sino que retorna una coleccion con los resultados de productos que pueden ser mas de 1 con 
el mismo nombre por ejemplo (mouse con cable, mouse inalambrico)

Eliminar Producto
DELETE /productos/{id}
Elimina un producto por ID,podemos hacer un Get

Servicios
ProductoService
crear(Producto producto): Crea un nuevo producto.
modificar(Producto producto): Modifica un producto existente.
buscarPorElIdONombre(String idONombre): Busca productos por ID o nombre.
eliminar(Integer id): Elimina un producto por ID.
listarPorPrecio(): Lista productos ordenados por precio de forma descendente.
Repositorio
ProductoRepository
findById(Long id): Busca un producto por ID.
findByNombreContainingIgnoreCase(String nombre): Busca productos por nombre.
findAllByOrderByPrecioDesc(): Lista productos ordenados por precio de forma descendente.
Se realizan validaciones para asegurar la integridad de los datos antes de guardar o modificar un producto.
Ejemplos de Tests
Se han incluido tests unitarios para el servicio de productos, cubriendo los siguientes casos:

Eliminar producto existente.
Eliminar producto no existente.

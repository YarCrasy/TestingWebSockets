public class App {
    public static void main(String[] args) throws Exception {
         HttpServer server = new HttpServer();
         server.start();
    }
}

/*
Ejercicio Práctico: Implementación de una API HTTP con Sockets y MySQL en Java

Objetivo:

Crear un servidor HTTP básico utilizando sockets en Java que gestione un endpoint `/books` con operaciones CRUD (GET, POST, PUT, DELETE) y almacene los datos en una base de datos MySQL. No se permite usar frameworks como Spring Boot; solo bibliotecas estándar de Java.

Requisitos:

Modelo de Datos:

   - Tabla `books` en MySQL con las columnas:  

     `id` (INT, PK, AUTO_INCREMENT), `title` (VARCHAR), `author` (VARCHAR), `year` (INT).

Endpoints HTTP:

   - GET /books: Devuelve todos los libros en formato JSON (200 OK).  

   - GET /books/{id}: Devuelve un libro por su ID (200 OK) o 404 si no existe.  

   - POST /books: Crea un nuevo libro. Recibe JSON en el cuerpo y devuelve el libro creado (201 Created).  

   - PUT /books/{id}: Actualiza un libro existente por ID. Devuelve 200 OK o 404.  

   - DELETE /books/{id}: Elimina un libro por ID. Devuelve 200 OK o 404.  

Tecnologías:

   - Java Sockets para manejar conexiones HTTP.  

   - JDBC para conectarse a MySQL.  

   - Respuestas HTTP válidas con cabeceras básicas (Content-Type: application/json).  

Pasos a Seguir:

Configuración Inicial:

   - Crea una base de datos MySQL llamada `library_db` y la tabla `books`. Proporciona un script SQL.  

   - Configura las credenciales de conexión a la base de datos en el código (usuario, contraseña, URL).

Servidor HTTP con Sockets:

   - Implementa un `ServerSocket` que escuche en el puerto 8080.  

   - Para cada conexión entrante, lee la solicitud HTTP, identifica el método (GET, POST, etc.) y la ruta.  

   - Parsea el cuerpo de la solicitud en caso de POST/PUT (formato JSON).  

Integración con MySQL:

   - Usa JDBC para ejecutar consultas según la operación solicitada (SELECT, INSERT, etc.).  

   - Asegura el manejo correcto de excepciones y cierre de recursos (Connection, PreparedStatement).  

Respuestas HTTP:

   - Genera respuestas con códigos de estado adecuados (ej: 200, 201, 404) y cuerpo en JSON.  

   - Ejemplo para GET /books:  

       HTTP/1.1 200 OK

     Content-Type: application/json

     [{"id": 1, "title": "El Quijote", "author": "Cervantes", "year": 1605}]

    

Entrega:

- Código fuente Java del servidor.  

- Script SQL para crear la base de datos y tabla.  

- Ejemplos de solicitudes HTTP (curl o Postman) para probar los endpoints.  

- Realizar una memoria explicativa en PDF, realizando un ejemplo de cada método HTTP al endpoint.

Recomendaciones:

- Usa `try-with-resources` para gestionar sockets y conexiones a la base de datos.  

- Implementa un manejador de rutas básico (ej: extraer el ID de `/books/5`).  

- Usa `BufferedReader` y `OutputStream` para leer/escribir datos en los sockets.  

Ejemplo de Solicitud POST:

POST /books HTTP/1.1

Content-Type: application/json

{

  "title": "Cien años de soledad",

  "author": "García Márquez",

  "year": 1967

}
*/
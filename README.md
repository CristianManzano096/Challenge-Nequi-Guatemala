# Proyecto Java - Ejecución Local

## Requisitos previos

Asegúrate de tener instalados los siguientes requisitos antes de ejecutar el proyecto localmente:

- **Java 17**: El proyecto está diseñado para ejecutarse con Java 17. Asegúrate de tener la versión correcta instalada. Puedes verificar tu versión de Java ejecutando:

  ```bash
  java -version
- **Gradle**: Este proyecto usa Gradle para gestionar las dependencias y la construcción del proyecto. Asegúrate de tener Gradle instalado. Puedes verificarlo ejecutando:
  ```bash
  gradle -v

- **DataBase-PostgreSQL**: Asegúrate de tener una base de datos PostgreSQL corriendo localmente o en un servidor accesible. Si estás usando Docker, puedes levantar un contenedor con PostgreSQL de la siguiente manera:
  ```bash
  docker run --name postgres-db -e POSTGRES_PASSWORD=yourpassword -d -p 5432:5432 postgres:latest

## Configuración de Variables de Entorno
El proyecto usa variables de entorno para gestionar las configuraciones de la base de datos y otros parámetros de configuración. Puedes definir estas variables en tu entorno local o en un archivo .env.

### Variables de Entorno
Las siguientes variables son necesarias para configurar correctamente la conexión a la base de datos:
- Estas se pueden configurar desde el archivo application.yaml ubicado en:
  ```bash 
  applications/app-service/src/main/resources/application.yaml

- DB_USER: El nombre de usuario para la base de datos.
- DB_PASSWORD: La contraseña para la base de datos.
- DB_HOST: La URL de la base de datos PostgreSQL (ejemplo: localhost o 127.0.0.1)
- DB_PORT: Puerto de la BD (ejemplo: 5432)
- DB_NAME: El nombre de usuario para la base de datos.
- DB_SCHEMA: Schema de la base de datos, en caso de que tenga, por defecto siempre es "public"

Hay variables que tiene por defecto y perfectamente se pueden usar esas para crear tu BD Local.

## Configuración de la Base de Datos 
- Para crear las tablas y unos ejemplos de registros, están a continuación.
  ```bash
  -- Tabla para la franquicia (franchise)
    CREATE TABLE public.franchise (id SERIAL PRIMARY KEY,name VARCHAR(255) NOT NULL);

  -- Tabla para las sucursales (branch)
    CREATE TABLE public.branch (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    franchise_id INTEGER REFERENCES public.franchise(id) ON DELETE CASCADE
    );

  -- Tabla para los productos (con clave foranea a la sucursal)
    CREATE TABLE public.product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock INTEGER,
    branch_id INTEGER REFERENCES public.branch(id) ON DELETE CASCADE
    );
  
  -- Insertar una franquicia
    INSERT INTO public.franchise (name)
    VALUES ('Franquicia A'), ('Franquicia B');
    
  -- Insertar sucursales y asociarlas con la franquicia
    INSERT INTO public.branch (name, franchise_id)
    VALUES ('Sucursal 1', 1), ('Sucursal 2', 2);
    
  -- Insertar productos y asociarlos con la sucursal
    INSERT INTO public.product (name, stock, branch_id)
    VALUES
    ('Producto A', 100, 1),
    ('Producto B', 200, 1),
    ('Producto C', 150, 2),
    ('Producto D', 80, 2);

## Ejecución del Proyecto Localmente

- Clona el proyecto desde el repositorio:
  ```bash 
  git clone https://github.com/CristianManzano096/Challenge-Nequi-Guatemala.git
  cd Challenge-Nequi-Guatemala
  
- Asegúrate de tener las dependencias del proyecto instaladas ejecutando:
  ```bash 
  gradle build

- Para ejecutar el proyecto, puedes usar el siguiente comando de Gradle:
  ```bash 
  gradle bootRun
Esto iniciará la aplicación Spring Boot en el puerto predeterminado (generalmente el puerto 8080).

- Otra opción es usar un IDE como Eclipse, Visual Code o IntelliJ, queda a libre elección, solo hay que configurarlo bien para un proyecto Spring 

## Ejecución del Proyecto con Docker (Opcional)
Puedes sentirte libre de modificar el Dockerfile, y si tienes experiencia con docker-compose es mucho mejor, ya que puedes orquestarlo con la BD.
En el proyecto se encuentran ambos archivos funcionales, el docker-compose usa una imagen que ya tengas cargada.

- Construir y ejecutar la imagen Docker con los siguientes comandos.
 ```bash
  gradle build
  docker build -t mi-aplicacion .
  docker run -p 8080:8080 mi-aplicacion

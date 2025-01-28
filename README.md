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
  

# Proyecto Java - Ejecución en la nube

Requisitos previos

Para la generación de la infraestructura de esta aplicación debes tener credenciales de AWS (AccessKey y SecretKey) y 
a demás tener instalado terraform en tu sistema operativo.

Una vez ya tengas estos dos requisitos, asegurate que tienes un rol IAM con los permisos suficientes para poder modificar
la infraestructura en la nube.

## Infraestructura de la Base de datos.

- Primero deberás dirigirte a la carpeta **terraform_db**, desde la carpeta root sería algo como lo siguiente
  ```bash
  cd ./terraform_db/RDS_Postgres
  
- En esta carpeta encontrarás dos infraestructuras, una para la BD y otra para el proyecto. vamos con la carpeta para 
generar la infraestructura de la RDS en postgres. Con tus credenciales de AWS dirigete al archivo de variables de 
terraform: terraform.tfvars y agrega esas credenciales en los espacios correspondientes. Verás que adicional hay 
información correspondiente a la base de datos como contraseña, username y dbname, puedes modificarlo si deseas.

- Si ya tienes todo lo necesario para ejecutar comandos de terraform solo hace falta ejecutar un par de comandos.
  ```bash
  terraform init # Inicia el terraform
  terraform plan # Puedes visualizar que hará terraform antes de crearlo
  terraform apply # Para que empiece a crear la infraestructura.
  
- Si todo sale bien durante la ejecución obtendrás tu BD en AWS con postrgres y el host lo imprimirá en la terminal, 
te aparecerá algo como lo siguiente: 
  ```bash
  mydb.123456789012.us-east-1.rds.amazonaws.com

## Infraestructura de la Aplicación.

- Para esta sección es importante contar con la Base de datos ya creada en la nube, dado que necesitamos el host y las 
demás propiedades de la misma.
- Al igual que como se mencionó anteriormente es necesario contar con el host de la BD y las credenciales de AWS con los
permisos suficientes para crear la infraestructura.
- Teniendo en cuenta lo anterior, verás que en la carpeta ECS encontrarás todo lo necesario para poder generar la 
infraestructura de la aplicación, podrás acceder a la carpeta desde la carpeta root con el siguiente comando
  ```bash
  cd ./terraform_db/ECS
- Al igual que en la infra para la BD encontrarás el archivo terraform.tfvars con las variables que necesitas, y aqui 
deberás agregar tus credenciales de aws y además la información de la base de datos, en caso de que hayas cambiado los 
datos como contraseña, username o dbname deberás cambiarlos a como los tienes en la Infra para la RDS.
- Si ya tienes todo lo necesario para ejecutar comandos de terraform solo hace falta ejecutar un par de comandos.
  ```bash
  terraform init # Inicia el terraform
  terraform plan # Puedes visualizar que hará terraform antes de crearlo
  terraform apply # Para que empiece a crear la infraestructura.
- Si todo sale bien durante la ejecución obtendras tu infraestructura ya creada y en la terminal verás la url del load
balancer, se vería algo como lo siguiente:
  ```bash
  lb-2070206059.us-east-1.elb.amazonaws.com
  # Una petición a esta url devuelve un status 200 con un mensaje de OK

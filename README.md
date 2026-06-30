# Proyecto AquaChiloe

Proyecto de microservicios hecho con Spring Boot. La idea es separar las partes principales de una operacion acuicola en servicios pequenos y comunicados entre si.

## Microservicios

| Microservicio | Puerto local | Responsabilidad |
| --- | --- | --- |
| ms-centros | 8080 | Registra jaulas y valida si existen |
| ms-biomasa | 8081 | Registra biomasa y consulta cantidad de peces a lotes |
| ms-lotes | 8082 | Registra lotes y valida jaulas con centros |
| ms-alimentacion | 8083 | Registra alimentacion y valida biomasa |
| ms-ambiental | 8084 | Registra lecturas ambientales |
| ms-sanidad | 8085 | Registra tratamientos sanitarios |
| ms-personal | 8086 | Registra trabajadores |
| ms-gateway | 8090 | Centraliza las rutas |

## Ejecutar con Docker

Desde la carpeta `Proyecto_AquaChiloe`:

```bash
docker compose up --build
```

Para detener:

```bash
docker compose down
```

## Swagger

Cada microservicio tiene Swagger habilitado:

| Servicio | Swagger |
| --- | --- |
| Centros | http://localhost:8080/swagger-ui.html |
| Biomasa | http://localhost:8081/swagger-ui.html |
| Lotes | http://localhost:8082/swagger-ui.html |
| Alimentacion | http://localhost:8083/swagger-ui.html |
| Ambiental | http://localhost:8084/swagger-ui.html |
| Sanidad | http://localhost:8085/swagger-ui.html |
| Personal | http://localhost:8086/swagger-ui.html |
| Gateway | http://localhost:8090/swagger-ui.html |

## Gateway

El gateway corre en:

```text
http://localhost:8090
```

Ejemplos de rutas:

```text
http://localhost:8090/gateway/centros/api/v1/centros/jaulas/1/verificar
http://localhost:8090/gateway/biomasa/api/v1/biomasa/total/1
http://localhost:8090/gateway/ambiental/api/v1/ambiental/status-critico/1
```

La configuracion del Gateway tambien esta en YAML:

```text
ms-gateway/src/main/resources/application.yml
```

## Manejo de errores

Cada microservicio tiene un paquete `exception` con un `GlobalExceptionHandler`. Esto deja respuestas mas ordenadas cuando ocurre un error.

## Base de datos

Cada microservicio usa H2 en memoria para desarrollo. La consola queda en:

```text
http://localhost:PUERTO/h2-console
```

Usuario:

```text
sa
```

Password vacio.

Tambien se agrego un script SQL de referencia en:

```text
sql/base_datos.sql
```

Ese archivo muestra las tablas principales y datos de ejemplo para probar.

## Postman

Se agrego una coleccion para probar las rutas del Gateway:

```text
postman/AquaChiloe.postman_collection.json
```

Para usarla:

1. Abrir Postman.
2. Importar el archivo JSON.
3. Levantar el proyecto con `docker compose up --build`.
4. Ejecutar las peticiones en orden, empezando por crear jaula.

## Pruebas y cobertura

Cada microservicio tiene pruebas con JUnit y reportes JaCoCo.

Ejecutar en un microservicio:

```bash
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```

Reporte:

```text
target/site/jacoco/index.html
```

## Render

Se dejo un archivo `render.yaml` para desplegar `ms-ambiental` como servicio web en Render.

Pasos generales:

1. Subir el repositorio a GitHub.
2. En Render, crear un nuevo Blueprint.
3. Seleccionar el repositorio.
4. Render usara `render.yaml`.
5. Revisar la URL publica generada.

## GitHub Flow usado

La forma recomendada de trabajo es:

```text
main
feature/docker
feature/swagger
feature/gateway
feature/tests
feature/docs
```

Cada funcionalidad se puede subir con Pull Request hacia `main`.

## ClickUp

Tareas sugeridas para el tablero:

| Tarea | Estado |
| --- | --- |
| Crear Dockerfiles | Listo |
| Crear docker-compose | Listo |
| Agregar Swagger | Listo |
| Crear Gateway | Listo |
| Agregar pruebas y JaCoCo | Listo |
| Agregar coleccion Postman | Listo |
| Agregar script SQL | Listo |
| Crear README e informe | Listo |
| Desplegar un microservicio en Render | Pendiente de cuenta Render |

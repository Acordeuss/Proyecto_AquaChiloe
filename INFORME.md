# Informe tecnico AquaChiloe

## 1. Descripcion general

El sistema AquaChiloe esta construido con microservicios en Spring Boot. Cada microservicio tiene una responsabilidad especifica y expone endpoints REST documentados con Swagger.

## 2. Arquitectura

La arquitectura esta formada por:

- Microservicios REST.
- API Gateway simple.
- Base de datos H2 por microservicio.
- Comunicacion HTTP con `RestTemplate`.
- Docker Compose para despliegue local.
- Paquete `exception` para manejo simple de errores.

## 3. Comunicacion entre microservicios

Se implementaron llamadas entre servicios:

| Origen | Destino | Uso |
| --- | --- | --- |
| ms-lotes | ms-centros | Validar si una jaula existe |
| ms-biomasa | ms-lotes | Obtener cantidad de peces |
| ms-alimentacion | ms-biomasa | Validar biomasa antes de alimentar |

Las URL se configuran con variables de entorno para que funcionen en Docker.

## 4. Manejo de errores

Cada servicio tiene manejo de errores con `try/catch`.

- `DataAccessException`: errores de base de datos.
- `RestClientException`: errores de comunicacion HTTP.

Esto permite mostrar errores mas claros y evitar que el sistema falle sin explicacion.

Ademas, cada microservicio tiene un `GlobalExceptionHandler` dentro del paquete `exception`.

## 5. Swagger

Cada microservicio tiene Swagger/OpenAPI habilitado en:

```text
/swagger-ui.html
/api-docs
```

## 6. Pruebas

Se usan pruebas con JUnit. Tambien se agrego JaCoCo para generar reportes de cobertura.

Comando:

```bash
mvnw.cmd test
```

Reporte:

```text
target/site/jacoco/index.html
```

## 7. Postman

Se agrego una coleccion de Postman en `postman/AquaChiloe.postman_collection.json`.
La coleccion prueba los endpoints principales usando el Gateway en `http://localhost:8090`.

## 8. Base de datos SQL

Aunque los microservicios usan H2 en memoria, se agrego el archivo `sql/base_datos.sql`.
Este archivo contiene las tablas principales y datos de ejemplo para dejar clara la estructura de la base de datos.

## 9. Docker

Cada microservicio tiene su propio `Dockerfile`. Para levantar todo:

```bash
docker compose up --build
```

El Gateway usa archivo YAML para sus propiedades principales:

```text
ms-gateway/src/main/resources/application.yml
```

## 10. Despliegue remoto

Se agrego `render.yaml` para desplegar `ms-ambiental` en Render. El despliegue final depende de subir el proyecto a GitHub y conectarlo con Render.

## 11. Conclusiones

El proyecto cumple con una estructura basica de microservicios: separacion por responsabilidades, documentacion Swagger, pruebas, base de datos por servicio, comunicacion HTTP, coleccion Postman, script SQL y despliegue local con Docker.

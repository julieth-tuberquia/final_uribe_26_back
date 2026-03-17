---
name: setup-backend
description: Prepares a Spring Boot backend by adding Springdoc OpenAPI (Swagger UI), configuring OpenAPI metadata, and aligning application.properties with a standard H2/JPA/Swagger template. Use when the user asks to add Swagger/OpenAPI docs, change Swagger paths (/docs, /api-docs), or standardize H2 + JPA properties for development.
---

# Setup backend (Swagger + H2 properties)

## Goal
Add Swagger/OpenAPI docs with Springdoc, configure paths, create `OpenApiConfig`, and standardize `application.properties` for an H2 in-memory DB.

## Workflow

### 1) Add Springdoc dependency
- In `pom.xml`, add Springdoc UI starter for Spring MVC:
  - `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- If the project does not manage the version, add a property (e.g. `springdoc.version`) and set it in the dependency.
- After editing, run Maven tests/build. If it fails due to incompatibility, bump/downgrade the Springdoc version until it compiles with the current Spring Boot version.

### 2) Configure Swagger paths
- In `src/main/resources/application.properties`, set:
  - `springdoc.swagger-ui.path=/docs`
  - `springdoc.api-docs.path=/api-docs`

### 3) Add OpenAPI metadata config
- Create `OpenApiConfig` under a config package (e.g. `...config`).
- Use `io.swagger.v3.oas.annotations.OpenAPIDefinition` + `@Info`:
  - title: `API Analítica`
  - version: `1.0`
  - description: `API para gestionar usuarios y análisis de datos`

### 4) Standardize application.properties for dev
- Keep existing domain code (modelos/DTOs/repos/controladores) untouched.
- Replace DB config with an H2 in-memory DB named `BDPruebaFinal`:
  - `spring.datasource.url=jdbc:h2:mem:BDPruebaFinal;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
  - `spring.datasource.driver-class-name=org.h2.Driver`
  - `spring.datasource.username=sa`
  - `spring.datasource.password=`
- Keep JPA config:
  - `spring.jpa.hibernate.ddl-auto=update`
  - `spring.jpa.show-sql=true`
  - `spring.jpa.properties.hibernate.format_sql=true`
- Keep H2 console:
  - `spring.h2.console.enabled=true`
  - `spring.h2.console.path=/h2-console`

### 5) Verify
- Run: `./mvnw.cmd test`
- Confirm Swagger UI loads at `/docs` and OpenAPI JSON is available at `/api-docs`.


# Facturas Sintéticas Mongo (Reactive Backend)

A Spring Boot WebFlux + Reactive MongoDB backend for managing synthetic invoice artifacts, prompts, templates, defects, and publicity data. Built with Java 17, Reactor, and Spring Data MongoDB Reactive.

## Features

- Reactive REST API (Mono/Flux) with Spring WebFlux
- MongoDB reactive persistence (Spring Data MongoDB Reactive)
- Organized domain: prompts (image/data/system/global-defect), basic templates, synthetic/publicity data, global defects
- Consistent list responses via a shared `ResponseUtil` helper
- CORS enabled for `http://localhost:4200` (Angular-friendly)
- Unit tests with JUnit 5, Mockito-style mocking, and Reactor Test
- Coverage with JaCoCo (HTML and XML reports)

## Tech Stack

- Java 17
- Spring Boot 4.0.0
  - spring-boot-starter-webflux
  - spring-boot-starter-data-mongodb-reactive
- Project Reactor (Mono/Flux)
- Testing: JUnit 5, reactor-test
- Coverage: JaCoCo (maven plugin)

## Project Structure

```
src/
  main/
    java/com/example/facturas_sinteticas_mongo/
      controller/       # REST controllers (WebFlux endpoints)
      dao/              # Reactive repositories (Spring Data interfaces)
      model/            # MongoDB documents (domain models)
      request/          # Request DTOs (payloads)
      response/         # Response DTOs (single + list wrappers)
      service/          # Services (business logic)
        sse/            # Server-Sent Events stream service (internal)
        utils/          # Shared utilities (ConverterUtil, ResponseUtil)
    resources/
      application.properties
  test/
    java/...            # Unit tests (services, controllers, utils, POJOs)
```

## Configuration

Application properties (default values shown):

```
spring.application.name=facturas_sinteticas_mongo
server.port=8083
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=facturas_sinteticas
# event name used by converter/SSE utilities
event.name.prompt.image=new-prompt-image
```

- Requires a MongoDB instance reachable at `localhost:27017` (or update the properties accordingly).
- CORS is configured at controller level for `http://localhost:4200`.

## Build & Run

Prerequisites:
- Java 17 installed and on PATH
- MongoDB running (default: localhost:27017)

Commands (using the Maven Wrapper):

```bash
# From project root
./mvnw spring-boot:run
```

Or build a jar and run:

```bash
./mvnw clean package
java -jar target/facturas_sinteticas_mongo-0.0.1-SNAPSHOT.jar
```

The application serves on `http://localhost:8083` by default.

## API Overview

Base path: `/mongo`

All controllers return reactive types and wrap results in response DTOs. Common patterns:
- Save/Update: `POST` returns the saved payload DTO.
- List All: `GET` returns a list wrapper (e.g., `All...Response`).
- Delete: `DELETE` accepts a request DTO with `id`, returns a single response DTO.

### Basic Templates
- POST `/mongo/save-basic-template`
  - Body: `{ id?, htmlString, cssString, name }`
  - Returns: saved `PromptGenerationRequest`-shaped DTO
- GET `/mongo/all-basic-template`
  - Returns: `{ basicTemplates: BasicTemplateResponse[] }`
- DELETE `/mongo/delete-basic-template`
  - Body: `{ id }`
  - Returns: `BasicTemplateResponse`
- POST `/mongo/get-basic-template`
  - Body: `{ id }`
  - Returns: `BasicTemplateResponse`

DTOs:
- BasicTemplateRequest: `{ id, htmlString, cssString, name }`
- BasicTemplateResponse: `{ id, htmlString, cssString, name }`
- AllBasicTemplateResponse: `{ basicTemplates: BasicTemplateResponse[] }`

### Synthetic Data Generation
- POST `/mongo/save-synthetic-data`
  - Body: `{ id?, data, name }`
  - Returns: saved `SyntheticDataGenerationRequest`
- GET `/mongo/all-synthetic-data`
  - Returns: `{ synthetics: SyntheticDataGenerationResponse[] }`
- DELETE `/mongo/delete-synthetic-data`
  - Body: `{ id }`
  - Returns: `SyntheticDataGenerationResponse`

DTOs:
- SyntheticDataGenerationRequest: `{ id, data, name }`
- SyntheticDataGenerationResponse: `{ id, data, name }`
- AllSyntheticDataGenerationResponse: `{ synthetics: SyntheticDataGenerationResponse[] }`

### Publicity Data Generation
- POST `/mongo/save-publicity-data`
  - Body: `{ id?, data, name }`
  - Returns: saved `SyntheticDataGenerationRequest`
- GET `/mongo/all-publicity-data`
  - Returns: `{ synthetics: SyntheticDataGenerationResponse[] }`
- DELETE `/mongo/delete-publicity-data`
  - Body: `{ id }`
  - Returns: `SyntheticDataGenerationResponse`

### Prompt Generation (Image)
- POST `/mongo/save-image-prompt`
  - Body: `{ id?, prompt, name }`
  - Returns: saved `PromptGenerationRequest`
- GET `/mongo/all-image-prompt`
  - Returns: `{ prompts: PromptGenerationResponse[] }`
- DELETE `/mongo/delete-image-prompt`
  - Body: `{ id }`
  - Returns: `PromptGenerationResponse`

### Prompt Generation (Data)
- POST `/mongo/save-data-prompt`
  - Body: `{ id?, prompt, name }`
  - Returns: saved `PromptGenerationRequest`
- GET `/mongo/all-data-prompt`
  - Returns: `{ prompts: PromptGenerationResponse[] }`
- DELETE `/mongo/delete-data-prompt`
  - Body: `{ id }`
  - Returns: `PromptGenerationResponse`

### Prompt Generation (System)
- POST `/mongo/save-system-prompt`
  - Body: `{ id?, prompt, name }`
  - Returns: saved `PromptGenerationRequest`
- GET `/mongo/all-system-prompt`
  - Returns: `{ prompts: PromptGenerationResponse[] }`
- DELETE `/mongo/delete-system-prompt`
  - Body: `{ id }`
  - Returns: `PromptGenerationResponse`

### Global Defects
- POST `/mongo/save-global-defect`
  - Body: `{ id?, category, defect, prompt }`
  - Returns: saved `GlobalDefect`
- GET `/mongo/all-global-defect`
  - Returns: `{ defects: GlobalDefectResponse[] }`
- DELETE `/mongo/delete-global-defect`
  - Body: `{ id }`
  - Returns: `GlobalDefectResponse`

### Prompt Global Defects
- POST `/mongo/save-global-defect-prompt`
  - Body: `{ id?, prompt, name }`
  - Returns: saved `PromptGenerationRequest`
- GET `/mongo/all-global-defect-prompt`
  - Returns: `{ prompts: PromptGenerationResponse[] }`
- DELETE `/mongo/delete-global-defect-prompt`
  - Body: `{ id }`
  - Returns: `PromptGenerationResponse`

## Data Model Summary

Representative models stored in MongoDB (simplified):
- BasicTemplateBill: `{ id, htmlString, cssString, name }`
- SyntheticDataGeneration / PublicityDataGeneration: `{ id, data, name }`
- PromptGenerationImage / Data / System / PromptGlobalDefect: `{ id, prompt, name }`
- GlobalDefect: `{ id, category, defect, prompt }`

Repositories are Spring Data reactive interfaces under `dao/` and are injected into corresponding services.

## Utilities

- `ResponseUtil.toListResponse(Flux<T>, Supplier<R>, BiConsumer<R,List<T>>)` standardizes list responses with `ResponseEntity.ok(...)` wrapping.
- `ConverterUtil` supports building `DataMessage` and Server-Sent Events (SSE) messages. An internal `SseGenerationDataService` exposes a `Flux<DataMessage>` stream for server-driven updates (no REST endpoint exposed in this module by default).

## Testing & Coverage

Run tests and generate JaCoCo coverage:

```bash
./mvnw clean test jacoco:report
```

Artifacts:
- HTML report: `target/site/jacoco/index.html`
- XML report:  `target/site/jacoco/jacoco.xml`

Notes:
- Tests use plain unit patterns (mocking services as needed) to avoid full autoconfiguration.
- During any `@SpringBootTest`, Mongo connection warnings may appear if no DB is up; they don’t fail isolated unit tests.

## Common Issues

- MongoDB not running: ensure a local instance or update `spring.data.mongodb.*` to your environment.
- CORS: default allows `http://localhost:4200`. Adjust `@CrossOrigin` on controllers for other frontends.
- Port conflicts: change `server.port` in `application.properties`.

## How To Extend

- Add new domain: create `model`, `request`, `response`, `dao` interface, and a `service` that maps requests to models and vice versa.
- Add endpoints: create a controller under `controller/`, inject the service, follow the existing save/list/delete patterns, and reuse `ResponseUtil` for list responses.

## License

This project’s code is provided without an explicit license in the repository. If you plan to redistribute or use it beyond local development, consider adding an appropriate license.

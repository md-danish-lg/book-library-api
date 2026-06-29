# 📚 Book Library API

A RESTful API for managing a personal book library, built with Java and Spring Boot. Supports full CRUD operations, genre filtering, marking books as read, and AI-powered book summarization via a dedicated Python AI microservice.

## Tech Stack

- **Java 21** + **Spring Boot 3**
- **Spring Data JPA** — data access layer
- **PostgreSQL** — relational database
- **Docker** — containerised database setup
- **RestTemplate** — service-to-service HTTP communication
- **JUnit 5** + **Mockito** + **AssertJ** — unit testing
- **GitHub Actions** — CI pipeline

## Architecture

Follows a clean three-layer architecture with an external AI microservice:

```
HTTP Request → Controller → Service → Repository → PostgreSQL
                               ↓
                        Python AI Service (FastAPI + Groq)
```

- **Controller** — handles HTTP requests and responses
- **Service** — business logic + delegates AI calls to Python service
- **Repository** — database communication via Spring Data JPA
- **AIService** — calls the Python FastAPI microservice over REST

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/book` | Get all books |
| `GET` | `/api/v1/book?genre=fiction` | Get books filtered by genre |
| `GET` | `/api/v1/book/{id}` | Get a single book by ID |
| `GET` | `/api/v1/book/{id}/summarize` | AI-generated summary via Python microservice |
| `POST` | `/api/v1/book` | Add a new book |
| `PUT` | `/api/v1/book/{id}` | Update a book (full replace) |
| `PATCH` | `/api/v1/book/{id}/read` | Mark a book as read |
| `DELETE` | `/api/v1/book/{id}` | Delete a book |

Returns `404` with a descriptive message when a book ID does not exist.

## AI Integration

The `/summarize` endpoint delegates to a separate Python FastAPI microservice that calls the Groq LLM API. Spring Boot calls the Python service using `RestTemplate` — a real polyglot microservice architecture.

The Python AI service must be running locally at `http://localhost:8000` for the summarize endpoint to work.

## Testing

Unit tests for the service layer using JUnit 5, Mockito, and AssertJ. The repository is mocked so tests run without a real database. CI runs on every push via GitHub Actions.

| Test | What it verifies |
|------|-----------------|
| `canGetAllBooks` | Service delegates to `repository.findAll()` |
| `getCorrectBookById` | Returns the correct book when ID exists |
| `throwWhenIdNotFound` | Throws `BookNotFoundException` with correct message |
| `markAsRead` | Sets `readStatus` to true and calls `repository.save()` |

Run tests:
```bash
./mvnw test
```

## Getting Started

### Prerequisites
- Java 21
- Maven
- Docker
- Python AI service running (see [python-ai-service](https://github.com/md-danish-lg/python-ai-service))

### Run the database
```bash
docker-compose up -d
```

### Run the application
```bash
./mvnw spring-boot:run
```

API available at `http://localhost:8080`

## Example Requests

**Add a book**
```bash
curl -X POST http://localhost:8080/api/v1/book \
  -H "Content-Type: application/json" \
  -d '{"title": "The Pragmatic Programmer", "author": "Andrew Hunt", "genre": "tech"}'
```

**Get AI summary of a book**
```bash
curl http://localhost:8080/api/v1/book/1/summarize
```

**Mark a book as read**
```bash
curl -X PATCH http://localhost:8080/api/v1/book/1/read
```

## What I Learned

- Spring Boot three-layer architecture
- Spring Data JPA with custom repository query methods
- Global exception handling with `@RestControllerAdvice`
- Service-to-service HTTP communication using `RestTemplate`
- Polyglot microservice architecture — Java backend calling a Python AI service
- Unit testing with JUnit 5, Mockito, and AssertJ
- CI/CD with GitHub Actions
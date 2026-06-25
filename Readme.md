# 📚 Book Library API

A RESTful API for managing a personal book library, built with Java and Spring Boot. Supports full CRUD operations, genre filtering, and marking books as read. Includes a unit test suite written with JUnit 5, Mockito, and AssertJ.


## Tech Stack

- **Java 21** + **Spring Boot 3**
- **Spring Data JPA** — data access layer
- **PostgreSQL** — relational database
- **Docker** — containerised database setup

## Architecture

Follows a clean three-layer architecture:

```
HTTP Request → Controller → Service → Repository → PostgreSQL
```

- **Controller** — handles HTTP requests and responses
- **Service** — business logic
- **Repository** — database communication via Spring Data JPA

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/book` | Get all books |
| `GET` | `/api/v1/book?genre=fiction` | Get books filtered by genre |
| `GET` | `/api/v1/book/{id}` | Get a single book by ID |
| `POST` | `/api/v1/book` | Add a new book |
| `PUT` | `/api/v1/book/{id}` | Update a book (full replace) |
| `PATCH` | `/api/v1/book/{id}/read` | Mark a book as read |
| `DELETE` | `/api/v1/book/{id}` | Delete a book |

Returns `404` with a descriptive message when a book ID does not exist.

## Testing

Unit tests for the service layer using JUnit 5, Mockito, and AssertJ. The repository is mocked so tests run without a real database.

| Test | What it verifies |
|------|-----------------|
| `canGetAllBooks` | Service delegates to `repository.findAll()` |
| `getCorrectBookById` | Returns the correct book when ID exists |
| `throwWhenIdNotFound` | Throws `BookNotFoundException` with correct message when ID doesn't exist |
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

### Run the database

```bash
docker-compose up -d
```

### Run the application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Example Requests

**Add a book**
```bash
curl -X POST http://localhost:8080/api/v1/book \
  -H "Content-Type: application/json" \
  -d '{"title": "The Pragmatic Programmer", "author": "Andrew Hunt", "genre": "tech"}'
```

**Get all fiction books**
```bash
curl http://localhost:8080/api/v1/book?genre=fiction
```

**Mark a book as read**
```bash
curl -X PATCH http://localhost:8080/api/v1/book/1/read
```

## What I Learned

- Spring Boot three-layer architecture (Controller → Service → Repository)
- Spring Data JPA with custom repository query methods
- Proper HTTP status codes and global exception handling with `@RestControllerAdvice`
- Partial updates with `PATCH` vs full replacement with `PUT`
- Running PostgreSQL via Docker Compose
- Unit testing the service layer with JUnit 5, Mockito mocks, and AssertJ assertions
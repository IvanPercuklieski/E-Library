# E-Library

A full-stack library management system built with **Spring Boot** (backend) and **Angular + Ionic** (frontend).

---

## Table of Contents

- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Backend (Server)](#backend-server)
  - [Technology Stack](#technology-stack)
  - [Running the Server](#running-the-server)
  - [Configuration](#configuration)
  - [Architecture](#architecture)
  - [Domain Model](#domain-model)
  - [Authentication & Security](#authentication--security)
  - [API Documentation](#api-documentation)
  - [Recommendation Engine](#recommendation-engine)
  - [Scheduled Jobs](#scheduled-jobs)
  - [Data Initialization](#data-initialization)
- [Frontend (Client)](#frontend-client)
  - [Technology Stack](#technology-stack-1)
  - [Running the Client](#running-the-client)
  - [Application Structure](#application-structure)
  - [Routing & Guards](#routing--guards)
  - [HTTP & Authentication Flow](#http--authentication-flow)

---

## Project Structure

```
E-Library/
├── Server/          # Spring Boot backend
└── Client/
    └── E-LibraryClient/   # Angular + Ionic frontend
```

---

## Getting Started

1. Start the **backend** first (port `8082`).
2. Start the **frontend** (port `4200`).
3. Log in with a seeded employee account (see [Data Initialization](#data-initialization)).

---

## Backend (Server)

### Technology Stack

| Component | Technology |
|---|---|
| Framework | Spring Boot 3.5.6 |
| Language | Java 17 |
| Database | H2 (in-memory) |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security + JWT (JJWT 0.11.5) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Email | Spring Mail (SMTP / Gmail) |
| Build | Maven Wrapper (`mvnw`) |
| Utilities | Lombok |

### Running the Server

```bash
cd Server

# Run in development mode
./mvnw spring-boot:run

# Build a JAR (skip tests)
./mvnw package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName
```

The server starts at **http://localhost:8082**.

### Configuration

All configuration lives in `src/main/resources/application.properties`.

| Property | Value |
|---|---|
| `server.port` | `8082` |
| `spring.datasource.url` | `jdbc:h2:mem:testdb` (in-memory, resets on restart) |
| `spring.jpa.hibernate.ddl-auto` | `create` |
| H2 Console | `http://localhost:8082/h2` |
| Swagger UI | `http://localhost:8082/swagger-ui/index.html` |

> **Email:** `spring.mail.password` is intentionally absent from the properties file. Provide it via an environment variable or a local override file before email notifications will work.

### Architecture

The backend follows a **two-layer service pattern**:

```
Controller  →  Application Service  →  Domain Service  →  Repository  →  H2
```

- **`web/controllers/`** — REST controllers, mapped to application services, return DTOs only.
- **`service/backend/application/`** — business logic, DTO mapping, orchestration. Each feature has an interface and an `impl/` implementation.
- **`service/domain/`** — thin CRUD wrappers over JPA repositories. Each feature has an interface and an `impl/` implementation.
- **`repository/`** — Spring Data JPA repositories.
- **`model/domain/`** — JPA entities.
- **`dto/`** — Data Transfer Objects, split into `create/`, `display/`, and `update/` sub-packages.
- **`config/`** — Security config, CORS config, OpenAPI config, and the data initializer.
- **`model/exceptions/`** — Domain-specific runtime exceptions (one per business rule violation).

### Domain Model

#### Books

| Entity | Description |
|---|---|
| `BaseBook` | The book template: title, author, genres (many-to-many), publication date, description, total copy count. |
| `BookCopy` | An individual physical copy of a `BaseBook`. This is what gets borrowed. |
| `Genre` | A book genre (e.g. Fantasy, Sci-Fi). |
| `Author` | A book author. |
| `Review` | A user review (text + rating) linked to a `BaseBook`. |

#### Borrowing

| Entity | Description |
|---|---|
| `BorrowedBook` | Represents an **active** loan — links a `UserWrapper` to a `BookCopy` with borrow and due timestamps. |
| `BookBorrowLog` | Immutable historical record of every borrow action. Used as input data for the ML recommendation engine. |

#### Users & Access

| Entity | Description |
|---|---|
| `UserWrapper` | A library member. Holds name, email, membership dates, `MembershipStatus` (`ACTIVE` / `EXPIRED`), and their list of active `BorrowedBook` records. |
| `Employee` | Implements Spring Security `UserDetails`. Linked 1:1 to a `UserWrapper`. Has a `username`, hashed `password`, and a role (`ADMIN` or `BASIC`). |

#### Physical Space

| Entity | Description |
|---|---|
| `Room` | A reading room with a name, location, and seat capacity. |
| `Seat` | A numbered seat inside a `Room`. Optionally assigned to a `UserWrapper`. |

### Authentication & Security

- Authentication is **stateless JWT**. The `JwtFilter` (`web/controllers/filters/`) validates the token on every request and sets the Spring Security context.
- The `Employee` entity is the Spring Security principal. Roles are `ADMIN` and `BASIC`.
- `WebSecurityConfig` permits all requests at the Spring Security level — access control is enforced at the frontend via route guards.
- CORS allows only `http://localhost:4200`.
- Passwords are stored BCrypt-hashed.

### API Documentation

Interactive Swagger UI is available at:

```
http://localhost:8082/swagger-ui/index.html
```

All REST endpoints are documented there with request/response schemas.

### Recommendation Engine

Located in `service/ml/`. When a `BookBorrowLog` is created and its transaction commits, a `BookBorrowLogCreatedEvent` is published. The `BookBorrowLogCreatedListener` then:

1. Calls `KnnGenreRecommender` — builds a genre-count vector for every user from their borrow history, finds the *k* nearest neighbours by cosine similarity, and aggregates their genre scores.
2. Passes the top recommendation to `NotificationService` (implemented by `EmailNotificationService`), which sends the user an email recommendation.

### Scheduled Jobs

`MembershipScheduler` runs a bulk SQL update every day at midnight (`0 0 0 * * *`) that sets `MembershipStatus = EXPIRED` for all `UserWrapper` records whose `dueDate` is in the past.

### Data Initialization

`DataInitializer` seeds the in-memory H2 database on every startup via `@PostConstruct`. The following data is inserted if not already present:

- **15 genres** (Fantasy, Sci-Fi, Romance, Mystery, etc.)
- **12 authors** (Tolkien, Orwell, Rowling, etc.)
- **16 library members** (`UserWrapper`)
- **14 books** with physical copies (`BaseBook` + `BookCopy`)
- **2 rooms** with seats (`Room` + `Seat`)
- **2 employee accounts** (see below)
- **Borrow logs** for ML testing

**Default login credentials:**

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | `ADMIN` |
| `obichen` | `ob123` | `BASIC` |

> To seed a large dataset (1 000 randomised borrow logs) for ML testing, uncomment `seedBookBorrowLogsBig()` and comment out `seedBookBorrowLogsSmall()` in `DataInitializer.init()`.

---

## Frontend (Client)

### Technology Stack

| Component | Technology |
|---|---|
| Framework | Angular 20 |
| UI Components | Ionic 8 |
| Language | TypeScript 5.8 |
| Auth | JWT (`jwt-decode`) |
| HTTP | Angular `HttpClient` with a functional interceptor |
| Testing | Karma + Jasmine |
| Linting | ESLint + angular-eslint |
| Build | Angular CLI 20 |

### Running the Client

```bash
cd Client/E-LibraryClient

# Install dependencies
npm install

# Start dev server at http://localhost:4200
npm start

# Production build (output to dist/)
npm run build

# Run unit tests
npm test

# Run a single spec file
npx ng test --include='**/my-component.spec.ts'

# Lint
npm run lint
```

### Application Structure

```
src/app/
├── core/
│   ├── guards/          # auth-guard, role-guard
│   ├── interceptors/    # api-interceptor (base URL + JWT header)
│   ├── models/          # TypeScript interfaces for all entities and DTOs
│   └── services/        # StorageService, TokenDecode
├── features/
│   ├── auth/            # Login, Register components + Auth service
│   ├── books/           # Book list, book details, search, featured, top-ten
│   ├── home-page/       # Home page
│   ├── admin-panel/     # Resource management (books, authors, genres, users, rooms, seats)
│   ├── loans/           # Active and historical loans management
│   └── seating/         # Seating grid, seating creator, seating view
└── shared/
    ├── components/      # Header
    └── services/        # Shared services
```

All feature modules are **lazy-loaded** via `loadChildren`.

### Routing & Guards

| Path | Feature | Access |
|---|---|---|
| `/home` | Home page | Public |
| `/books` | Books catalogue | Public |
| `/seating` | Seating view | Public |
| `/resources` | Admin panel (resource management) | ADMIN or BASIC |
| `/loans` | Loans management | ADMIN or BASIC |

`/admin-panel` is an alias that redirects to `/resources`. Any unknown path redirects to `/home`.

The `roleGuard` functional guard checks that the user is authenticated and has one of the required roles. If not, it redirects to `/home`.

### HTTP & Authentication Flow

1. On login, the backend returns a JWT. The frontend stores it in `localStorage` under the key `currentUser` via `StorageService`.
2. `api-interceptor.ts` clones every outgoing request, prepends the base URL `http://localhost:8082/`, and attaches the header `Authorization: Bearer <token>` if a token is present.
3. On HTTP `401`, the interceptor removes `currentUser` from storage (automatic logout).
4. `TokenDecode` decodes the JWT payload (using `jwt-decode`) to read claims such as role and user ID without a server round-trip.
5. `Auth` service exposes `isAuthenticated()` and `hasAnyRole(roles)` which are used by the route guards.

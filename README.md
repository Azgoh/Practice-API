# Spring Boot Practice API

This is my first project building a REST API using the Spring Boot framework. There is no clear goal in mind for this API other than to familiarize myself with some of the core concepts. Please note that this project is very much still in development. Let's get started!

## Architecture

This project is built using the **Layered Architecture** (N-tier architecture) aiming to separate concerns and improve maintainability and scalability. In the layered architecture, layers are arranged in a hierarchical manner, with each layer interacting only with the layers directly above or below it and each layer has a clear responsibility:

- Presentation Layer --> Controller
- Business Layer --> Service
- Persistence Layer --> Repository
- Data Layer --> Entity

The responsibility of each layer is as follows:

- **component** → Contains utility classes, filters, or helper beans (e.g., JWT filters, token utilities).
- **config** → Configuration classes (security, Swagger, etc.)
- **controller** → Handles HTTP requests and responses. Delegates business logic to the service layer and communicates using DTOs. No business logic or direct repository access here.
- **dto** → Encapsulate data exchanged between layers and clients, exposing only necessary fields and hiding internal entity structures.
- **entity** → Represent persistent data structures and database tables using JPA annotations.
- **enumeration** → Enums for roles, status, and other constants
- **exception** → Global exception handler (@RestControllerAdvice) ensures consistent error responses and centralizes exception management.
- **mapper** → Converts between entities and DTOs, centralizing mapping logic for consistency.
- **repository** → Interfaces directly with the database using Spring Data JPA. Handles CRUD operations and custom queries.
- **service** → Implements core business rules, orchestrates workflows, and applies validations. Interacts with repositories and converts entities to DTOs.



The package structure is the following:

``` graphql 
com.example.PracticeApi
├── component    # Utility classes, filters, helper beans
├── config       # Configuration classes (security, Swagger, etc.)
├── controller   # REST controllers handling HTTP requests
├── dto          # Data Transfer Objects for communication between layers
├── entity       # JPA entities mapping to database tables
├── enumeration  # Enums for roles, status, and other constants
├── exception    # Custom exceptions and global exception handler
├── mapper       # Mapping classes between entities and DTOs
├── repository   # Spring Data JPA repository interfaces
├── service      # Business logic layer implementing application rules
```

## Features

- User registration with email verification
- User registration and login via Google
- JWT-based login and authorization
- Role-based access (User vs Professional)
- Professional profile registration
- Availability management
- Review management
- Appointment management
- File management
- Global exception handling
- Swagger UI for API documentation.

## Tech Stack

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL
- Swagger / OpenAPI
- Lombok

## Getting Started
### Prerequisites

- Java 17
- Maven
- PostgreSQL

To run the project you will first need to create a database using postgres. Then, make sure to generate a [JWT secret key](https://jwtsecrets.com/), as well as [set up your gmail](https://support.google.com/mail/answer/7104828?hl=en) in order for it to be able to send email verifications. After following the instructions, you will need to generate an app password, so go to https://myaccount.google.com/security and make sure to enable 2-step verification. Then google *app password*, sign in to your google account and generate an app password. After that, open the project with your preferred IDE ~~Intellij~~, create a .env file and export the necessary information so application.properties can use it (your database name, your postgres username and password, your jwt secret, your gmail and your app password).  Only pass the app password that you generated in the *spring.mail.password* field, do **not** pass your gmail password.

### Build & Run

Open a terminal and run:

``` bash
mvn clean install
mvn spring-boot:run
```

After running the app, open http://localhost:8080/swagger-ui/index.html and you will find the ~~incomplete~~ documentation of the API.
# Spring Boot Practice API

This is my first project building a REST API using the Spring Boot framework. There is no clear goal in mind for this API other than to familiarize myself with some of the core concepts. Please note that this project is very much still in development. Let's get started!

## Architecture

This project is built using the **Layered Architecture** (N-tier architecture) aiming to separate concerns and improve maintainability and scalability. In the layered architecture, layers are arranged in a hierarchical manner, with each layer interacting only with the layers directly above or below it and each layer has a clear responsibility:

- Presentation Layer --> Controller
- Business Layer --> Service
- Persistence Layer --> Repository
- Data Layer --> Entity

The responsibility of each layer is as follows:

- **Controller Layer** --> Handles HTTP requests and responses. It only depends on the service layer, to which it delegates the business logic, and communicates using DTOs.
- **Service Layer** --> Contains core business rules. It interacts with repositories and applies logic before returning results to the controller.
- **Repository Layer** --> Interfaces with the database using Spring Data JPA.
- **DTOs** --> Used to transfer data between layers and expose only necessary fields to the client.
- **Entities** --> Represent persistent data structures mapped to database tables using JPA.
- **Exception handling** --> Global exception handler ensures consistent error responses and cleaner code.
- **Security Layer** --> JWT-based authentication and authorization using Spring Security, with role-based access control.

The package structure is the following:

``` graphql 
com.example.PracticeApi
├── config        # Configuration classes
├── controller    # REST controllers
├── dto           # Data transfer objects
├── entity        # JPA entities
├── enumeration   # Enums (roles, etc.)
├── exception     # Custom exceptions + handler
├── repository    # Spring Data JPA interfaces
├── security      # JWT filters and auth
├── service       # Business logic
```

## Features

- User registration with email verification
- JWT-based login and authorization
- Role-based access
- Professional registration
- File management
- Add and fetch ratings for professionals
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

To run the project you will first need to create a database using postgres. Then, make sure to generate a [JWT secret key](https://jwtsecrets.com/), as well as [set up your gmail](https://support.google.com/mail/answer/7104828?hl=en) in order for it to be able to send email verifications. After following the instructions, you will need to generate an app password, so go to https://myaccount.google.com/security and make sure to enable 2-step verification. Then google *app password*, sign in to your google account and generate an app password. After that, open the project with your preferred IDE ~~Intellij~~ and go to **src/main/resources/application.properties** and pass the necessary information (your database name, your postgres username and password, your jwt secret, your gmail and your app password).  Only pass the app password that you generated in the *spring.mail.password* field, do **not** pass your gmail password.

### Build & Run

Open a terminal and run:

``` bash
mvn clean install
mvn spring-boot:run
```

After running the app, open http://localhost:8080/swagger-ui/index.html and you will find the ~~incomplete~~ documentation of the API.
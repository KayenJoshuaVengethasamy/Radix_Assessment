# Radix_Assessment

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-green)
![Maven](https://img.shields.io/badge/Maven-3.9.6-orange)
A Loan Payment System within a single Spring Boot application
***

## Quick Start

```bash
git clone https://github.com/KayenJoshuaVengethasamy/Radix_Assessment.git
cd Radix_Assessment

./mvnw clean install
./mvnw spring-boot:run
```

API available at:

```text
http://localhost:8080
```

POSTMAN scripts :
```text
LoanPaymentSystem.postman_collection.json
```

## Table of Contents

- [Overview](#overview)
- [Assumptions](#assumptions)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Error Handling](#error-handling)
- [Running Tests](#running-tests)
- [H2 Console](#h2-console)

***

## Overview

The system exposes REST APIs across two logically separated domains:

- **Loan Domain** — create and retrieve loans
- **Payment Domain** — record payments against loans, with automatic balance reduction and settlement detection

Business rules enforced:
- A loan must have an initial amount and term
- Payments reduce the loan's remaining balance
- A payment that exceeds the remaining balance is rejected
- When a loan is fully paid, it is automatically moved to `SETTLED` status

***

## Assumptions

- Loan terms are expressed in months.
- Interest calculations are out of scope.
- Payments are applied directly to the outstanding balance.
- Partial payments are allowed.
- Overpayments are rejected.
- A loan is automatically marked as `SETTLED` when the remaining balance reaches zero.

## Tech Stack

| Technology              | Version | Purpose                       |
|-------------------------|---------|-------------------------------|
| Java                    | 17      | Language                      |
| Spring Boot             | 4.1.0   | Application framework         |
| Spring Web MVC          | Managed | REST API layer                |
| Spring Data JPA         | Managed | Database persistence (ORM)    |
| Spring Validation       | Managed | Request body validation       |
| H2 Database             | Managed | In-memory embedded database   |
| Lombok                  | Managed | Boilerplate reduction         |
| JUnit 5                 | Managed | Unit testing framework        |
| Mockito                 | 5.23.0  | Mocking framework             |
| Maven                   | 3.9.6   | Build and dependency tool     |

***

## Project Structure

```
com.radix.assessment
├── AssessmentApplication.java
├── common
│   ├── constants
│   │   ├── ErrorConstants.java
│   │   └── loans
│   │       └── LoanStatus.java
│   └── exception
│       └── model
│           ├── CustomException.java
│           ├── GlobalExceptionHandler.java
│           └── DTO
│               └── response
│                   └── ErrorResponse.java
├── loans
│   ├── controller
│   │   └── LoanController.java
│   ├── loader
│   │   └── LoanDataLoader.java
│   ├── model
│   │   ├── Loan.java
│   │   └── DTO
│   │       ├── request
│   │       │   └── LoanRequest.java
│   │       └── response
│   │           ├── LoanResponse.java
│   │           └── MapToLoanResponse.java
│   ├── repository
│   │   └── LoanRepository.java
│   └── services
│       ├── LoanService.java
│       └── implementation
│           └── LoanServiceImplementation.java
└── payments
    ├── controller
    │   └── PaymentController.java
    ├── model
    │   ├── Payment.java
    │   └── DTO
    │       ├── request
    │       │   └── PaymentRequest.java
    │       └── response
    │           └── PaymentResponse.java
    ├── repository
    │   └── PaymentRepository.java
    └── services
        ├── PaymentService.java
        └── implementation
            └── PaymentServiceImplementation.java
```

***

## Architecture

The application follows a **layered architecture** within a single Spring Boot project, with two logically separated domains.

### Domain Separation

```
┌─────────────────────────────────────────────────────┐
│                   HTTP Clients                      │
└───────────────┬─────────────────┬───────────────────┘
                │                 │
        POST/GET /loans    POST /payments
                │                 │
┌───────────────▼─────────────────▼───────────────────┐
│              Controllers (API Layer)                │
│         LoanController │ PaymentController          │
└───────────────┬─────────────────┬───────────────────┘
                │                 │
┌───────────────▼─────────────────▼───────────────────┐
│              Services (Business Logic)              │
│      LoanServiceImpl  │  PaymentServiceImpl         │
└───────────────┬─────────────────┬───────────────────┘
                │                 │
┌───────────────▼─────────────────▼───────────────────┐
│            Repositories (Data Layer)                │
│        LoanRepository  │  PaymentRepository         │
└───────────────┬─────────────────┬───────────────────┘
                │                 │
┌───────────────▼─────────────────▼───────────────────┐
│              H2 In-Memory Database                  │
│           loans table  │  payments table            │
└─────────────────────────────────────────────────────┘
```

### Layers

| Layer          | Responsibility                                                                                          |
|----------------|---------------------------------------------------------------------------------------------------------|
| **Controller** | Accepts HTTP requests, validates input via `@Valid`, delegates to service, returns `ResponseEntity`     |
| **Service**    | Owns all business logic — balance reduction, overpayment checks, SETTLED transition                     |
| **Repository** | Data access via Spring Data JPA — no custom queries needed                                              |
| **Model**      | JPA entities (`Loan`, `Payment`), DTOs for request/response, enum for `LoanStatus`                     |
| **Common**     | Cross-cutting concerns — `ErrorConstants`, `CustomException`, `GlobalExceptionHandler`                  |

### Key Design Decisions

- **Interface-backed services** — controllers depend on `LoanService` and `PaymentService` interfaces, not implementations, keeping the code testable and open for extension
- **DTO separation** — request and response objects are separate from JPA entities, preventing internal model leakage to API consumers
- **Single custom exception** — `CustomException` wraps all business errors with an `ErrorConstants` enum, keeping error definitions in one place and ensuring consistent HTTP status codes
- **Transactional payments** — `PaymentServiceImpl.recordPayment()` is annotated with `@Transactional`, ensuring the payment record and loan balance update are committed atomically
- **Validation at the boundary** — `@Valid` on controller method parameters means invalid requests are rejected before reaching the service layer

***

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.x

Verify your versions:

```bash
java -version
mvn -version
```

### Clone the Repository

```bash
git clone https://github.com/KayenJoshuaVengethasamy/Radix_Assessment.git
cd Radix_Assessment
```

### Build the Application

```bash
./mvnw clean install
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application starts on **http://localhost:8080**.

The H2 in-memory database is created automatically on startup and seeded with two sample loans via `LoanDataLoader`.

***

## API Endpoints

### Base URL

```
http://localhost:8080
```

***

### Loan Domain

#### Create a Loan

```
POST /loans
```

**Request Body:**

```json
{
    "loanAmount": 10000.00,
    "term": 12
}
```

| Field        | Type       | Required | Description                     |
|--------------|------------|----------|---------------------------------|
| `loanAmount` | BigDecimal | Yes      | Total loan amount (must be > 0) |
| `term`       | Integer    | Yes      | Loan duration in months (> 0)   |

**Response — 201 Created:**

```json
{
    "loanId": 1,
    "loanAmount": 10000.00,
    "remainingBalance": 10000.00,
    "term": 12,
    "status": "ACTIVE"
}
```

***

#### Get Loan Details

```
GET /loans/{loanId}
```

**Response — 200 OK:**

```json
{
    "loanId": 1,
    "loanAmount": 10000.00,
    "remainingBalance": 8000.00,
    "term": 12,
    "status": "ACTIVE"
}
```

***

### Payment Domain

#### Record a Payment

```
POST /payments
```

**Request Body:**

```json
{
    "loanId": 1,
    "paymentAmount": 2000.00
}
```

| Field           | Type       | Required | Description                       |
|-----------------|------------|----------|-----------------------------------|
| `loanId`        | Long       | Yes      | ID of the loan to pay against     |
| `paymentAmount` | BigDecimal | Yes      | Amount to pay (must be > 0)       |

**Response — 201 Created:**

```json
{
    "paymentId": 1,
    "loanId": 1,
    "paymentAmount": 2000.00,
    "remainingBalance": 8000.00,
    "loanStatus": "ACTIVE"
}
```

When the final payment is made and the balance reaches zero, `loanStatus` returns as `SETTLED`.

***

### Testing with curl

**Create a loan:**

```bash
curl -X POST http://localhost:8080/loans \
  -H "Content-Type: application/json" \
  -d '{"loanAmount": 10000.00, "term": 12}'
```

**Get loan details:**

```bash
curl http://localhost:8080/loans/1
```

**Make a payment:**

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"loanId": 1, "paymentAmount": 2000.00}'
```

**Settle a loan in full:**

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"loanId": 1, "paymentAmount": 8000.00}'
```

***

### Testing with Postman

The project includes a Postman collection (LoanPaymentSystem.postman_collection.json) that can be used to manually test the API and verify both successful and error scenarios.

#### Prerequisites

Before running the collection:

1. Ensure the application is running locally.

2. Verify the API is accessible at:

   ```
   http://localhost:8080
   ```

3. Install Postman if it is not already installed.

#### Importing the Collection

1. Open Postman.

2. Click **Import** in the top-left corner.

3. Select the file:

   ```
   Radix-LoanPaymentSystem.postman_collection.json
   ```

4. The collection will appear in your Postman workspace.

#### Collection Configuration

The collection contains a predefined variable:

| Variable  | Value                   |
| --------- | ----------------------- |
| `baseUrl` | `http://localhost:8080` |

If the application is running on a different host or port, update the `baseUrl` collection variable before executing the requests.

#### Running Individual Requests

The collection is designed so that requests can be executed one by one in sequence.

1. Start with **1. Create Loan (Valid)**.
2. The test script automatically extracts the returned `loanId` and stores it as a collection variable.
3. All subsequent requests use this stored `loanId`.
4. Continue executing the remaining requests in numerical order.

#### Running the Full Test Suite

To execute all tests automatically:

1. Select the collection in Postman.
2. Click **Run Collection**.
3. Ensure all 13 requests are selected.
4. Click **Run Radix Assessment - Loan Payment System**.

The Collection Runner will execute each request in sequence and display the results.

#### Test Coverage

The collection validates the following scenarios:

##### Loan Operations

* Create a loan successfully
* Create a loan with an invalid amount
* Create a loan with missing required fields
* Retrieve an existing loan
* Attempt to retrieve a non-existent loan

##### Payment Operations

* Record a valid partial payment
* Verify the remaining balance is updated
* Prevent overpayment
* Settle a loan in full
* Verify the loan status changes to `SETTLED`
* Prevent payments on a settled loan
* Prevent payments on a non-existent loan
* Validate payment amount constraints

#### Expected Results

A successful execution should show all tests passing in the Postman Collection Runner.

The collection also includes negative test cases that intentionally trigger validation and business rule errors. These requests are expected to return error responses (for example, `400`, `404`, or `422`) and will be marked as passed when the correct error response is returned.

#### Notes

* Request order is important because later requests depend on the `loanId` generated by the first request.
* If a request fails unexpectedly, verify that the application is running and that the database is in a clean state.
* If required, rerun **1. Create Loan (Valid)** to generate a new loan before executing dependent requests.


***

## Error Handling

All errors return a consistent JSON structure:

```json
{
    "status": 404,
    "message": "No loan found for loan ID: 999"
}
```

| Scenario                          | HTTP Status | Error                                                           |
|-----------------------------------|-------------|-----------------------------------------------------------------|
| Loan not found                    | `404`       | `No loan found for loan ID: X`                                  |
| Payment exceeds remaining balance | `422`       | `Payment amount of X.XX exceeds remaining loan balance of Y.YY` |
| Loan already settled              | `422`       | `Loan X is already settled. No further payments accepted.`      |
| Invalid request body              | `400`       | Field-level validation message                                  |
| Unexpected server error           | `500`       | `An unexpected error occurred`                                  |

***

## Running Tests

Run all unit tests:

```bash
./mvnw test
```

Run a specific test class:

```bash
./mvnw test -Dtest=LoanServiceTest
./mvnw test -Dtest=PaymentServiceTest
```

### Test Coverage

| Test Class          | Scenarios Covered                                                                 |
|---------------------|-------------------------------------------------------------------------------------------------|
| `LoanServiceTest`   | Loan creation, status set to ACTIVE, balance equals loan amount, persistence, loan retrieval,   |
|                     | not found                                                                                       |
| `PaymentServiceTest`| Partial payment, balance reduction, full settlement, SETTLED state persistence, overpayment     |
|                     | error, payment on settled loan, loan not found                                                  |

***

## H2 Console

The H2 in-memory database console is available at runtime for inspection:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:assessmentDB
Username: sa
Password: (leave blank)
```

Two sample loans are pre-loaded on startup by `LoanDataLoader`:
- **Loan 1** — R500,000 over 60 months, status `ACTIVE`
- **Loan 2** — R500 over 60 months, status `SETTLED`

> **Note:** The H2 database is in-memory only. All data is lost when the application stops.
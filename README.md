# REST Assured + Cucumber BDD API Test Framework

A behavior-driven API test automation framework combining **Cucumber (Gherkin)**, **REST Assured**, **TestNG**, and **Maven** — tested against the public sandbox API [reqres.in](https://reqres.in/).

## Why this project

This is the BDD counterpart to a plain-TestNG REST Assured project. It demonstrates how to structure API tests so that scenarios are readable in plain English (Gherkin) by non-technical stakeholders, while the underlying implementation still uses a clean, layered Java structure — a service layer, POJOs for serialization/deserialization, and JSON schema validation.

## Tech Stack

- Java 11
- Cucumber 7.15 (Gherkin + Java step definitions)
- REST Assured 5.4
- TestNG 7.10 (as the runner engine for Cucumber)
- Maven
- Jackson (POJO serialization/deserialization)

## Project Structure

```
src/main/java/com/kaustubh/
├── models/
│   ├── CreateUserRequest.java      # POJO for request bodies (serialization)
│   ├── UserResponse.java            # POJO for GET response (deserialization)
│   └── CreatedUserResponse.java     # POJO for POST/create response (deserialization)
├── services/
│   └── UserService.java             # All API calls live here — the "Page Object" of API testing
└── utils/
    └── RequestSpecFactory.java      # Centralized base URI/headers/logging config

src/test/java/com/kaustubh/
├── stepdefinitions/
│   └── UserApiSteps.java             # Glue code — maps Gherkin steps to Java/REST Assured calls
└── runners/
    └── TestNGCucumberRunner.java     # Ties Cucumber into TestNG/Maven so it runs in CI like any suite

src/test/resources/
├── features/
│   └── user_api.feature              # Scenarios written in plain-English Gherkin
└── schemas/
    └── single-user-schema.json       # JSON schema used for structural response validation

testng.xml
```

## Why a Service Layer instead of calling REST Assured directly in steps

This plays the same role for API testing that a **Page Object** plays for UI testing: step definitions never build a raw request themselves — they call a method on `UserService`. If an endpoint path or request shape changes, there's exactly one place to update, instead of hunting through every step definition that touches that endpoint.

## What's covered (`user_api.feature`)

| Scenario | What it demonstrates |
|---|---|
| Fetch a single existing user | Basic GET + status/field assertions |
| Deserialize single user response | Response mapped into a `UserResponse` POJO, asserted on typed fields |
| Single user response matches schema | JSON schema validation — structural contract check |
| Fetch a paginated list of users | GET collection with query parameters |
| Requesting a non-existent user returns 404 | Negative case |
| Create a new user | POST with a POJO request body (serialization) |
| Update an existing user's job title | PUT / update flow |
| Delete an existing user | DELETE — correct no-content status |
| Create a user then validate extracted name | Chained request — using data from one response in a follow-up assertion |

## Running the tests

```bash
git clone <this-repo-url>
cd rest-assured-cucumber-bdd-framework
mvn clean test
```

An HTML Cucumber report is generated at `target/cucumber-reports/cucumber.html` after each run.

## Possible Next Steps

- Add Cucumber tags (`@smoke`, `@regression`) to `testng.xml` for selective execution in CI
- Add Allure reporting alongside the built-in Cucumber HTML report
- Add a GitHub Actions workflow to run the suite on every push
- Add authentication flow scenarios (Bearer token) once tested against an API that requires it

# AWSE project at the DHBW 2021

This project is part of the lecture advanced software engineering (AWSE). The course content and exercises include clean architecture, DDD, programming
principles, testing, refactoring and design patterns.

## Topic

The goal was to implement a simple Pacman game, with all course content implemented. The backend is based on Spring Boot, the frontend on VueJs. The
communication of both takes place via REST as well as via websockets (RSocket).

## Setup

### What you need

- Java 8
- Maven
- NPM

### FIRST: Run the backend

```bash
mvn clean install -DskipTests=true
cd 0-plugins-boot
mvn org.springframework.boot:spring-boot-maven-plugin:run
```

runs in background on port 8080

### SECOND: Run the frontend

further details for the first initialization see [vuejs-frontend](vuejs-frontend/README.md)

```bash
cd .. #go back to the root of this project
cd vuejs-frontend
npm run dev
```

access the frontend within the browser on port 8081

## Running the tests

Go to the root directory of this project and run the following command.
(This can also be executed for each module individually)

```bash
mvn test
```

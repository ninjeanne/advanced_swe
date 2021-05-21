# AWSE project at the DHBW 2021

This project is part of the lecture advanced software engineering (AWSE). The course content and exercises include clean architecture, DDD, programming
principles, testing, refactoring and design patterns.

## Topic

The goal was to implement a simple Pacman game, with all course content implemented. The backend is based on Spring Boot, the frontend on VueJs. The
communication of both takes place via REST as well as via websockets (RSocket).

## Run the backend

```bash
cd 0-plugins-boot
mvn compile -DskipTests=true
```

## Run the frontend

```
cd vuejs-frontend
npm run dev
```

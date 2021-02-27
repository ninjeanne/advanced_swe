# AWSE project at the DHBW 2021

This project is part of the lecture advanced software engineering (AWSE). The course content and exercises include clean architecture, DDD, programming
principles, testing, refactoring and design patterns.

## Topic

The goal was to implement a simple Pacman game, with all course content implemented. The backend is based on Spring Boot, the frontend on VueJs. The
communication of both takes place via REST as well as via websockets (RSocket).

## Run the project

```
mvn clean package spring-boot:run -DskipTests=true
```

## RSocket

further details can be found here: https://spring.io/blog/2020/03/02/getting-started-with-rsocket-spring-boot-server
and the example project is located
here: https://github.com/benwilcock/spring-rsocket-demo/blob/master/rsocket-server/src/main/java/io/pivotal/rsocketserver/RSocketController.java

### download the CLI here

```
wget -O rsc.jar https://github.com/making/rsc/releases/download/0.4.2/rsc-0.4.2.jar
```

### start the CLI

```
java -jar rsc.jar --help
```

### run an example request

```
java -jar rsc.jar --debug --request --data "{\"origin\":\"Client\",\"interaction\":\"Request\"}" --route request-response tcp://localhost:7000
```

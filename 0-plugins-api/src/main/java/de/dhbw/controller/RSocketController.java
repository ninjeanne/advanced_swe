package de.dhbw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Controller
public class RSocketController {

    static final String SERVER = "Server";

    @MessageMapping("request-response")
    Mono<Message> requestResponse(final Message request) {
        log.info("Received request-response request: {}", request);
        // create a single Message and return it
        return Mono.just(new Message(SERVER, "response", 0));//todo adapt message constructor here
    }

    @MessageMapping("testSocketMessageMapping")
    public Flux<Message> getByAuthor(Message message) {
        System.out.println(message);
        return Flux
                // create a new indexed Flux emitting one element every second
                .interval(Duration.ofSeconds(1))
                // create a Flux of new Messages using the indexed Flux
                .map(index -> new Message(SERVER, "Server says hello | " + message.getMessage(), index));
    }
}


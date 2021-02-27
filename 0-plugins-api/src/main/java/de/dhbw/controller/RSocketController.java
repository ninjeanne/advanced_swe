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
    static final String RESPONSE = "Response";
    static final String STREAM = "Stream";

    @MessageMapping("request-response")
    Mono<Message> requestResponse(final Message request) {
        log.info("Received request-response request: {}", request);
        // create a single Message and return it
        return Mono.just(new Message(SERVER, RESPONSE));
    }

    @MessageMapping("stream")
    Flux<Message> stream(final Message request) {
        log.info("Received stream request: {}", request);
        System.err.println("Received: " + request.getMessage());

        return Flux
                // create a new indexed Flux emitting one element every second
                .interval(Duration.ofSeconds(1))
                // create a Flux of new Messages using the indexed Flux
                .map(index -> new Message(SERVER, STREAM, index));
    }

    @MessageMapping("hello")
    public Mono<Message> helloServer(Message message) {
        log.info("got message in Server {}", message.getMessage());
        return Mono.just(message).map(msg -> new Message(msg.getMessage() + " | Server says hello!"));
    }

    @MessageMapping("tweets.by.author")
    public Flux<Message> getByAuthor(Message message) {
        System.out.println(message);
        return Flux
                // create a new indexed Flux emitting one element every second
                .interval(Duration.ofSeconds(1))
                // create a Flux of new Messages using the indexed Flux
                .map(index -> new Message(SERVER, STREAM, index));
    }
}


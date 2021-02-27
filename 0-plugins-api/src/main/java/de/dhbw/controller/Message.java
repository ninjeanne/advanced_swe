package de.dhbw.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String origin;
    private long index;
    private long created = Instant.now().getEpochSecond();
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message(String origin, String message, long index) {
        this.origin = origin;
        this.message = message;
        this.index = index;
    }
}

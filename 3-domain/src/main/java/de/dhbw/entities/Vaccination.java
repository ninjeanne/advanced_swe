package de.dhbw.entities;

import org.springframework.stereotype.Component;

@Component
public class Vaccination extends GameObject {

    @Override
    public int getRankingValue() {
        return 0;
    }

    @Override
    public int getDefaultNumberOfObjects() {
        return 3;
    }
}

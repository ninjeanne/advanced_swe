package de.dhbw.entities;

import org.springframework.stereotype.Component;

@Component
public class Vaccination extends GameObject {
    @Override
    public void doToPlayer(PlayerEntity playerEntity) {
       super.doToPlayer(playerEntity);
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public int getDefaultNumberOfObjects() {
        return 3;
    }
}

package de.dhbw.entities;

import org.springframework.stereotype.Component;

@Component
public class WorkItem extends GameObject {
    @Override
    public void doToPlayer(PlayerEntity playerEntity) {
        super.doToPlayer(playerEntity);
    }

    @Override
    public int getValue() {
        return 50;
    }
}

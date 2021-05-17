package de.dhbw.entities;

public class Vaccination extends GameObject {
    @Override
    public void doToPlayer(PlayerEntity playerEntity) {
       super.doToPlayer(playerEntity);
    }

    @Override
    public int getValue() {
        return 0;
    }
}

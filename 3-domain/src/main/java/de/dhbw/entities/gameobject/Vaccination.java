package de.dhbw.entities.gameobject;

import org.springframework.stereotype.Component;

@Component
public class Vaccination extends GameObjectEntity {

    @Override
    public int getRankingValue() {
        return 0;
    }

    @Override
    public int getDefaultNumberOfObjects() {
        return 3;
    }
}

package de.dhbw.entities.gameobjects;

import org.springframework.stereotype.Component;

@Component
public class VaccinationEntity extends GameObjectEntity {

    @Override
    public int getRankingValue() {
        return 0;
    }

    @Override
    public int getDefaultNumberOfObjects() {
        return 3;
    }
}

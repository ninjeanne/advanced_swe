package de.dhbw.entities.gameobject;

import org.springframework.stereotype.Component;

@Component
public class WorkItem extends GameObjectEntity {
    @Override
    public int getRankingValue() {
        return 50;
    }
}

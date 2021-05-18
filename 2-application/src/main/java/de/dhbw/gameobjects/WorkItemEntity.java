package de.dhbw.gameobjects;

import de.dhbw.entities.gameobjects.GameObjectEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkItemEntity extends GameObjectEntity {
    @Override
    public int getRankingValue() {
        return 50;
    }
}

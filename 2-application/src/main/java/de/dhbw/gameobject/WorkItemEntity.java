package de.dhbw.gameobject;

import de.dhbw.entities.gameobject.GameObjectEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkItemEntity extends GameObjectEntity {
    @Override
    public int getRankingValue() {
        return 50;
    }
}

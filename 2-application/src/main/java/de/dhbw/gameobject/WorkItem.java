package de.dhbw.gameobject;

import de.dhbw.entities.gameobject.GameObjectEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkItem extends GameObjectEntity {
    @Override
    public int getRankingValue() {
        return 50;
    }
}

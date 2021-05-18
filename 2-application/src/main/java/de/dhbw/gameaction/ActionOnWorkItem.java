package de.dhbw.gameaction;

import de.dhbw.domainservice.gameaction.GameAction;
import de.dhbw.gameobject.WorkItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem extends GameAction<WorkItemEntity> {
    public ActionOnWorkItem() {
        super(WorkItemEntity.class);
    }
}

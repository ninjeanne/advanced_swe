package de.dhbw.actions;

import de.dhbw.domainservice.GameAction;
import de.dhbw.gameobjects.WorkItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem extends GameAction<WorkItemEntity> {
    public ActionOnWorkItem() {
        super(WorkItemEntity.class);
    }
}

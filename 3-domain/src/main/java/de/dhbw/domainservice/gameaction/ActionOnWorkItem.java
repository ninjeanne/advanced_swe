package de.dhbw.domainservice.gameaction;

import de.dhbw.entities.gameobject.WorkItem;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem extends GameAction<WorkItem> {
    public ActionOnWorkItem() {
        super(WorkItem.class);
    }
}

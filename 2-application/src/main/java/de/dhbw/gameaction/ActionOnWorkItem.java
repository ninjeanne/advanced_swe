package de.dhbw.gameaction;

import de.dhbw.domainservice.gameaction.GameAction;
import de.dhbw.gameobject.WorkItem;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem extends GameAction<WorkItem> {
    public ActionOnWorkItem() {
        super(WorkItem.class);
    }
}

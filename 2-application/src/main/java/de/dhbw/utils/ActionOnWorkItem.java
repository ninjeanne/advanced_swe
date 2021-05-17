package de.dhbw.utils;

import de.dhbw.entities.WorkItem;
import de.dhbw.helper.GameAction;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem extends GameAction<WorkItem> {
    public ActionOnWorkItem() {
        super(WorkItem.class);
    }
}

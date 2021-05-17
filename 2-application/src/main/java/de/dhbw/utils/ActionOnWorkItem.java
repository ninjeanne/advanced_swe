package de.dhbw.utils;

import de.dhbw.entities.WorkItem;
import de.dhbw.helper.GameAction;
import de.dhbw.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem extends GameAction<WorkItem> {

    private final PlayerService playerService;

    @Autowired
    public ActionOnWorkItem(PlayerService playerService) {
        super(WorkItem.class);
        this.playerService = playerService;
    }

    @Override
    public void doActionOn(WorkItem workItem) {
        workItem.doToPlayer(playerService.getCurrentPlayer());
        //todo add new item
    }
}

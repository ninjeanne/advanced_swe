package de.dhbw.utils;

import de.dhbw.helper.GameAction;
import de.dhbw.services.PlayerService;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnNewPosition implements GameAction {

    private final PlayerService playerService;

    @Autowired
    public ActionOnNewPosition(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void doActionOn(CoordinatesVO coordinatesVO) {
        playerService.setNewPosition(coordinatesVO);
    }
}

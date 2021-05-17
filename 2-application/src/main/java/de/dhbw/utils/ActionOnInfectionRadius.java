package de.dhbw.utils;

import de.dhbw.entities.Infection;
import de.dhbw.helper.GameAction;
import de.dhbw.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnInfectionRadius extends GameAction<Infection> {

    private final PlayerService playerService;

    @Autowired
    public ActionOnInfectionRadius(PlayerService playerService) {
        super(Infection.class);
        this.playerService = playerService;
    }

    public void doActionOn(Infection infection) {
       infection.doToPlayer(playerService.getCurrentPlayer());
       //todo is in infection radius
    }
}

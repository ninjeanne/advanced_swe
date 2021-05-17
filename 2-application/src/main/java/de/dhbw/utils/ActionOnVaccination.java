package de.dhbw.utils;

import de.dhbw.entities.Vaccination;
import de.dhbw.helper.GameAction;
import de.dhbw.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnVaccination extends GameAction<Vaccination> {

    private final PlayerService playerService;

    @Autowired
    public ActionOnVaccination(PlayerService playerService) {
        super(Vaccination.class);
        this.playerService = playerService;
    }

    @Override
    public void doActionOn(Vaccination vaccination) {
        vaccination.doToPlayer(playerService.getCurrentPlayer());
        //todo add new item
    }
}

package de.dhbw.actions;

import de.dhbw.domainservice.GameAction;
import de.dhbw.entities.gameobjects.InfectionEntity;
import org.springframework.stereotype.Component;

@Component
public class ActionOnInfectionRadius extends GameAction<InfectionEntity> {
    public ActionOnInfectionRadius() {
        super(InfectionEntity.class);
    }
}

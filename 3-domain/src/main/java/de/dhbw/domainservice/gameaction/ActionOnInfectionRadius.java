package de.dhbw.domainservice.gameaction;

import de.dhbw.entities.gameobject.InfectionEntity;
import org.springframework.stereotype.Component;

@Component
public class ActionOnInfectionRadius extends GameAction<InfectionEntity> {
    public ActionOnInfectionRadius() {
        super(InfectionEntity.class);
    }
}

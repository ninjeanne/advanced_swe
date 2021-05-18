package de.dhbw.domainservice.gameaction;

import de.dhbw.entities.gameobject.Infection;
import org.springframework.stereotype.Component;

@Component
public class ActionOnInfectionRadius extends GameAction<Infection> {
    public ActionOnInfectionRadius() {
        super(Infection.class);
    }
}

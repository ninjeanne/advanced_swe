package de.dhbw.utils;

import de.dhbw.entities.Infection;
import de.dhbw.helper.GameAction;
import org.springframework.stereotype.Component;

@Component
public class ActionOnInfectionRadius extends GameAction<Infection> {
    public ActionOnInfectionRadius() {
        super(Infection.class);
    }
}

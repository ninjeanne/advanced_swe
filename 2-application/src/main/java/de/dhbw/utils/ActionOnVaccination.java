package de.dhbw.utils;

import de.dhbw.entities.Vaccination;
import de.dhbw.helper.GameAction;
import org.springframework.stereotype.Component;

@Component
public class ActionOnVaccination extends GameAction<Vaccination> {
    public ActionOnVaccination() {
        super(Vaccination.class);
    }
}

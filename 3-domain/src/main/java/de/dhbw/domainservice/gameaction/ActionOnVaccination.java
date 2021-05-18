package de.dhbw.domainservice.gameaction;

import de.dhbw.entities.gameobject.Vaccination;
import org.springframework.stereotype.Component;

@Component
public class ActionOnVaccination extends GameAction<Vaccination> {
    public ActionOnVaccination() {
        super(Vaccination.class);
    }
}

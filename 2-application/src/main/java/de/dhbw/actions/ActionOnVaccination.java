package de.dhbw.actions;

import de.dhbw.domainservice.GameAction;
import de.dhbw.entities.gameobjects.VaccinationEntity;
import org.springframework.stereotype.Component;

@Component
public class ActionOnVaccination extends GameAction<VaccinationEntity> {
    public ActionOnVaccination() {
        super(VaccinationEntity.class);
    }
}

package de.dhbw.domainservice.gameaction;

import de.dhbw.entities.gameobject.VaccinationEntity;
import org.springframework.stereotype.Component;

@Component
public class ActionOnVaccination extends GameAction<VaccinationEntity> {
    public ActionOnVaccination() {
        super(VaccinationEntity.class);
    }
}

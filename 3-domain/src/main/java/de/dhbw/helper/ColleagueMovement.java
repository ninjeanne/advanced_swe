package de.dhbw.helper;

import de.dhbw.entities.ColleagueEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface ColleagueMovement {
    CoordinatesVO nextPosition();
    ColleagueEntity getColleagueEntity();
    CoordinatesVO getCurrentPosition();
}

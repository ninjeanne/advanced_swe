package de.dhbw.domainservice;

import de.dhbw.entities.BoardEntity;
import de.dhbw.valueobjects.CoordinatesVO;


public interface BoardDomainService {
    void initializeBoard(String boardName);
    boolean isInitialized();
    void startMovingColleagues();

    boolean isCoordinateEmpty(CoordinatesVO coordinatesVO);

    boolean isVaccination(CoordinatesVO coordinatesVO);
    void addRandomVaccinationToBoard();

    boolean isWorkItem(CoordinatesVO coordinatesVO);
    void addRandomWorkItemToBoard();

    boolean isInInfectionRadius(CoordinatesVO coordinatesVO);
    boolean infectByProbability();

    BoardEntity getCurrentBoard();

    void reset();
    void stopMovingColleagues();
}

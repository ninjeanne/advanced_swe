package de.dhbw.domainservice;

import de.dhbw.entities.board.BoardEntity;
import de.dhbw.valueobjects.CoordinatesVO;


public interface BoardDomainService {
    boolean isCoordinateEmpty(CoordinatesVO coordinatesVO);

    BoardEntity getCurrentBoard();

    void reset();
}

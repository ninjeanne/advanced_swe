package de.dhbw.domainservice;

import de.dhbw.entities.board.BoardEntity;
import de.dhbw.entities.gameobjects.GameObjectEntity;
import de.dhbw.valueobjects.CoordinatesVO;

import java.util.List;

public interface BoardDomainService extends MoveColleaguesDomainService, ResetService, InitializerService {
    void initializeBoard(String boardName);
    boolean isCoordinateEmpty(CoordinatesVO coordinatesVO);
    BoardEntity getCurrentBoard();
    List<GameObjectEntity> getGameObjects();
}

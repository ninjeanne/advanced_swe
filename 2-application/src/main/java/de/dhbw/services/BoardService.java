package de.dhbw.services;

import de.dhbw.domainservice.BoardDomainService;
import de.dhbw.entities.board.BoardEntity;
import de.dhbw.entities.board.ColleagueEntity;
import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.entities.gameobject.Infection;
import de.dhbw.helper.ColleagueMovement;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class BoardService implements BoardDomainService {
    private final BoardRepository boardRepository;
    private BoardEntity boardEntity;
    private List<ColleagueMovement> forwardAndBackMovements;
    private Timer colleagueMovementTimer;
    private final List<GameObjectEntity> gameObjectEntities;

    @Autowired
    public BoardService(BoardRepository boardRepository, List<GameObjectEntity> gameObjectEntities) {
        this.boardRepository = boardRepository;
        this.gameObjectEntities = gameObjectEntities;
    }

    @Override
    public boolean isInitialized() {
        return boardEntity != null && forwardAndBackMovements != null;
    }

    @Override
    public void reset() {
        stopMovingColleagues();
        boardEntity = null;
        forwardAndBackMovements = null;
    }

    @Override
    public List<GameObjectEntity> getGameObjects(){
        return gameObjectEntities;
    }

    @Override
    public void initializeBoard(String boardName) {
        this.boardEntity = boardRepository.getBoardByName(boardName);
        this.forwardAndBackMovements = new ArrayList<>();
        for (ColleagueEntity colleague : boardEntity.getColleagues()) {
            forwardAndBackMovements.add(colleague.createColleagueIterator());
        }
        initializeCoordinatesForGameObjects();
    }

    private void initializeCoordinatesForGameObjects() {
        for (GameObjectEntity gameObjectEntity : getGameObjects()) {
            CoordinatesVO coordinatesVO;
            do {
                coordinatesVO = getCurrentBoard().getBoardLayout().getRandomCoordinate();
            } while (!isCoordinateEmpty(coordinatesVO));
            gameObjectEntity.setNewCoordinate(coordinatesVO);
        }
    }

    public BoardEntity getCurrentBoard() {
        return boardEntity;
    }

    public boolean isCoordinateEmpty(CoordinatesVO coordinatesVO) {
        boolean isEmpty = !boardEntity.getBoardLayout().isCoordinateBlocked(coordinatesVO);
        for (ColleagueMovement iterator : this.forwardAndBackMovements) {
            if (iterator.getCurrentPosition().equals(coordinatesVO)) {
                return false;
            }
        }
        return isEmpty;
    }

    public List<ColleagueMovement> getColleagueMovements() {
        return new ArrayList<>(forwardAndBackMovements); //forbid changing the original list
    }

    public void startMovingColleagues() {
        this.forwardAndBackMovements = new ArrayList<>();
        boardEntity.getColleagues().forEach(value -> {
            this.forwardAndBackMovements.add(value.createColleagueIterator());
        });

        TimerTask rankingPointTask = new TimerTask() {
            List<Infection> currentInfections = new ArrayList<>();

            public void run() {
                clearLastInfectionPositions();
                forwardAndBackMovements.forEach(movement -> {
                    addNewInfectionFor(movement.nextPosition());
                });
            }

            private void clearLastInfectionPositions(){
                getGameObjects().removeAll(currentInfections);
                currentInfections = new ArrayList<>();
            }

            private void addNewInfectionFor(CoordinatesVO colleaguesNextPosition) {
                Infection newInfection = new Infection();
                newInfection.setBoardConfigurationEntity(boardEntity.getBoardConfiguration());
                newInfection.setNewCoordinate(colleaguesNextPosition);
                currentInfections.add(newInfection);
                getGameObjects().add(newInfection);
            }
        };
        colleagueMovementTimer = new Timer("Colleague Movement Timer");
        colleagueMovementTimer.scheduleAtFixedRate(rankingPointTask, 0, 500);
    }

    public void stopMovingColleagues() {
        colleagueMovementTimer.cancel();
        colleagueMovementTimer = null;
    }
}

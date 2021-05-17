package de.dhbw.services;

import de.dhbw.domainservice.BoardDomainService;
import de.dhbw.domainservice.MoveColleaguesDomainService;
import de.dhbw.entities.*;
import de.dhbw.helper.ColleagueMovement;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardService implements BoardDomainService, MoveColleaguesDomainService {
    private final BoardRepository boardRepository;
    private BoardEntity boardEntity;
    private List<ColleagueMovement> forwardAndBackMovements;
    private final Map<Class<? extends GameObject>, GameObject> gameObjects = new HashMap<>();
    private Timer colleagueMovementTimer;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public boolean isInitialized() {
        return boardEntity != null && forwardAndBackMovements != null;
    }

    public void reset() {
        stopMovingColleagues();
        boardEntity = null;
        forwardAndBackMovements = null;
    }

    public void initializeBoard(String boardName) {
        this.boardEntity = boardRepository.getBoardByName(boardName);
        this.forwardAndBackMovements = new ArrayList<>();
        initializeGameObjects(new Vaccination(), new WorkItem());
        for (ColleagueEntity colleague : boardEntity.getColleagues()) {
            forwardAndBackMovements.add(colleague.createColleagueIterator());
        }
    }

    public Map<Class<? extends GameObject>, GameObject> getGameObjects(){
        return this.gameObjects;
    }

    public void initializeGameObjects(GameObject... gameObjects) {
        for (GameObject gameObject : gameObjects) {
            CoordinatesVO coordinatesVO;
            do {
                coordinatesVO = boardEntity.getBoardLayout().getRandomCoordinate();
            } while (!isCoordinateEmpty(coordinatesVO));
            gameObject.setNewCoordinate(coordinatesVO);
            this.gameObjects.put(GameObject.class, gameObject);
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
            public void run() {
                forwardAndBackMovements.forEach(ColleagueMovement::nextPosition);
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

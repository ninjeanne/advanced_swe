package de.dhbw.services;

import de.dhbw.domainservice.BoardDomainService;
import de.dhbw.domainservice.MoveColleaguesDomainService;
import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.ColleagueEntity;
import de.dhbw.helper.ColleagueMovement;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class BoardService implements BoardDomainService, MoveColleaguesDomainService {
    private final BoardRepository boardRepository;
    private BoardEntity boardEntity;
    private List<ColleagueMovement> forwardAndBackMovements;
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
        for (ColleagueEntity colleague : boardEntity.getColleagues()) {
            forwardAndBackMovements.add(colleague.createColleagueIterator());
        }
    }

    public boolean isVaccination(CoordinatesVO coordinatesVO) {
        return coordinatesVO.equals(boardEntity.getVaccination());
    }

    public boolean isWorkItem(CoordinatesVO coordinatesVO) {
        return coordinatesVO.equals(boardEntity.getWorkItem());
    }

    public boolean isInInfectionRadius(CoordinatesVO coordinatesVO) {
        for (ColleagueMovement iterator : this.forwardAndBackMovements) {
            CoordinatesVO colleaguePosition = iterator.getCurrentPosition();

            if (colleaguePosition.distanceTo(coordinatesVO) <= boardEntity.getColleagueRadius().getRadius()) {
                return true;
            }
        }

        return false;
    }

    public BoardEntity getCurrentBoard() {
        return boardEntity;
    }

    public void addRandomVaccinationToBoard() {
        PlanVO plan = boardEntity.getPlan();
        CoordinatesVO coordinatesVO;
        do {
            int x = (int) (Math.random() * plan.getWidth());
            int y = (int) (Math.random() * plan.getHeight());
            coordinatesVO = new CoordinatesVO(x, y);
        } while (boardEntity.addNewVaccination(coordinatesVO));
    }

    public void addRandomWorkItemToBoard() {
        PlanVO plan = boardEntity.getPlan();
        CoordinatesVO coordinatesVO;
        do {
            int x = (int) (Math.random() * plan.getWidth());
            int y = (int) (Math.random() * plan.getHeight());
            coordinatesVO = new CoordinatesVO(x, y);
        } while (boardEntity.addNewWorkItem(coordinatesVO));
    }

    public boolean infectByProbability() {
        return Math.random() >= boardEntity.getInfectProbability().getProbability();
    }

    public boolean isCoordinateEmpty(CoordinatesVO coordinatesVO) {
        boolean isEmpty = boardEntity.containsCoordinate(coordinatesVO) && !boardEntity.getObstacles().contains(coordinatesVO);
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

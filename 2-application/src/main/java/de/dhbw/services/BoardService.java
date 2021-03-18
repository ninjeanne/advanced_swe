package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.domainservice.BoardDomainService;
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
public class BoardService implements BoardDomainService {
    private final BoardRepository boardRepository;
    private BoardAggregate boardAggregate;
    private List<ColleagueMovement> colleagueMovements;
    private Timer colleagueMovementTimer;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public boolean isInitialized() {
        return boardAggregate != null && colleagueMovements != null;
    }

    public void reset() {
        stopMovingColleagues();
        boardAggregate = null;
        colleagueMovements = null;
    }

    public void initializeBoard(String boardName) {
        this.boardAggregate = boardRepository.getBoardByName(boardName);
        this.colleagueMovements = new ArrayList<>();
        for (ColleagueAggregate colleague : boardAggregate.getColleagues()) {
            colleagueMovements.add(colleague.createColleagueIterator());
        }
    }

    public boolean isVaccination(CoordinatesVO coordinatesVO) {
        return coordinatesVO.equals(boardAggregate.getVaccination());
    }

    public boolean isWorkItem(CoordinatesVO coordinatesVO) {
        return coordinatesVO.equals(boardAggregate.getWorkItem());
    }

    public boolean isInInfectionRadius(CoordinatesVO coordinatesVO) {
        for (ColleagueMovement iterator : this.colleagueMovements) {
            CoordinatesVO colleaguePosition = iterator.getCurrentPosition();

            if (colleaguePosition.distanceTo(coordinatesVO) <= boardAggregate.getColleagueRadius().getRadius()) {
                return true;
            }
        }

        return false;
    }

    public BoardAggregate getCurrentBoard() {
        return boardAggregate;
    }

    public void addRandomVaccinationToBoard() {
        PlanVO plan = boardAggregate.getPlan();
        CoordinatesVO coordinatesVO;
        do {
            int x = (int) (Math.random() * plan.getWidth());
            int y = (int) (Math.random() * plan.getHeight());
            coordinatesVO = new CoordinatesVO(x, y);
        } while (boardAggregate.addNewVaccination(coordinatesVO));
    }

    public void addRandomWorkItemToBoard() {
        PlanVO plan = boardAggregate.getPlan();
        CoordinatesVO coordinatesVO;
        do {
            int x = (int) (Math.random() * plan.getWidth());
            int y = (int) (Math.random() * plan.getHeight());
            coordinatesVO = new CoordinatesVO(x, y);
        } while (boardAggregate.addNewWorkItem(coordinatesVO));
    }

    public boolean infectByProbability() {
        return Math.random() >= boardAggregate.getInfectProbability().getProbability();
    }

    public boolean isCoordinateEmpty(CoordinatesVO coordinatesVO) {
        boolean isEmpty = boardAggregate.containsCoordinate(coordinatesVO) && !boardAggregate.getObstacles().contains(coordinatesVO);
        for (ColleagueMovement iterator : this.colleagueMovements) {
            if (iterator.getCurrentPosition().equals(coordinatesVO)) {
                return false;
            }
        }
        return isEmpty;
    }

    public List<ColleagueMovement> getColleagueMovements() {
        return new ArrayList<>(colleagueMovements); //forbid changing the original list
    }

    public void startMovingColleagues() {
        this.colleagueMovements = new ArrayList<>();
        boardAggregate.getColleagues().forEach(value -> {
            this.colleagueMovements.add(value.createColleagueIterator());
        });
        TimerTask rankingPointTask = new TimerTask() {
            public void run() {
                colleagueMovements.forEach(ColleagueMovement::nextPosition);
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

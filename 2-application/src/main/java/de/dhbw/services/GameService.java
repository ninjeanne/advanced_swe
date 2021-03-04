package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.domainservice.GameDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService implements GameDomainService {

    private final BoardRepository boardRepository;

    private PlayerEntity player;
    private RankingEntity rankingEntity;
    private BoardAggregate board;
    private Date date;
    private boolean running = false;

    private Timer rankingPointTimer;
    private Timer colleagueMovementTimer;

    @Autowired
    public GameService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void initializeGame(String playerName, String boardName) {
        if (!running) {
            BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
            initialize(boardAggregate);
            initialize(PlayerEntity.builder().name(playerName).build());
            initializeDate();
            initialize(new RankingEntity(UUID.randomUUID().toString(), this.player.getName(), 0, date));
            return;
        }

        throw new RuntimeException("Game can't be initialized because it's already running.");
    }

    private void initializeLifePointsForPlayer() {
        for (int i = 0; i < 3; i++) {
            player.increaseLifePoints();
        }
    }

    @Override
    public void initialize(PlayerEntity player) {
        if (!running) {
            this.player = player;
            initializeLifePointsForPlayer();
            this.player.setPosition(new CoordinatesVO(0, 0));//todo init better/random position
            return;
        }

        throw new RuntimeException("Player can't be changed when the game is running");
    }

    @Override
    public void initialize(BoardAggregate board) {
        if (!running) {
            this.board = board;
            return;
        }

        throw new RuntimeException("Board can't be changed when the game is running");
    }

    @Override
    public void initializeDate() {
        this.date = new Date();
    }

    @Override
    public void initialize(RankingEntity rankingEntity) {
        this.rankingEntity = rankingEntity;
    }

    @Override
    public boolean isInitialized() {
        return date != null && board != null && player != null && rankingEntity != null;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void vaccinatePlayer() {
        if (isRunning()) {
            if (player.isAlive()) {
                player.increaseLifePoints();
                return;
            }
            stop();
            return;
        }

        throw new RuntimeException("Game hasn't been started yet.");
    }

    @Override
    public void infectPlayer() {
        if (isRunning()) {
            if (player.isAlive()) {
                player.decreaseLifePoints();
                if (player.isAlive()) {
                    return;
                }
            }
            stop();
            return;
        }

        throw new RuntimeException("Game hasn't been started yet.");
    }

    @Override
    public int getLastRankingPointsForPlayer() {
        if (rankingEntity != null) {
            return rankingEntity.getEarned_points();
        }

        throw new RuntimeException("There is no saved ranking for this game and user");
    }

    @Override
    public int getLifePointsForPlayer() {
        if (isRunning()) {
            return player.getLifePoints();
        }
        throw new RuntimeException("Game hasn't been started yet");
    }

    @Override
    public void startCountingRankingPointsForPlayer() {
        if (isRunning() && rankingPointTimer == null) {
            TimerTask rankingPointTask = new TimerTask() {
                public void run() {
                    rankingEntity = new RankingEntity(UUID.randomUUID().toString(), player.getName(), rankingEntity.getEarned_points() + 20,
                            rankingEntity.getDate());
                    System.out.println("Rankingpoints are " + rankingEntity.getEarned_points());
                }
            };
            rankingPointTimer = new Timer("Increase Ranking Points");
            long delay = 1000L;
            rankingPointTimer.schedule(rankingPointTask, delay);
        }
    }

    @Override
    public void stopCountingRankingPointsForPlayer() {
        rankingPointTimer.cancel();
        rankingPointTimer = null;
    }

    @Override
    public boolean movePlayer(CoordinatesVO newCoordinates) {
        if (board.containsCoordinate(newCoordinates) && !board.getObstacles().contains(newCoordinates)) {
            player.setPosition(newCoordinates);
            return true;
        }
        return false;
    }

    @Override
    public RankingEntity getLastRankingForPlayer() {
        if (rankingEntity != null) {
            return rankingEntity;
        }
        throw new RuntimeException("There is no last ranking for this game instance.");
    }

    @Override
    public BoardAggregate getCurrentBoard() {
        return board;
    }

    public void initDefaultBoard() {
        if (player != null) {
            initializeDate();
            initialize(new RankingEntity(UUID.randomUUID().toString(), player.getName(), 0, date));
            initialize(boardRepository.getBoardByName("default"));
            return;
        }

        throw new RuntimeException("The initialization of the default board needs a player.");
    }

    @Override
    public void start() {
        if (isInitialized()) {
            running = true;
            startCountingRankingPointsForPlayer();
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            stopCountingRankingPointsForPlayer();
            stopMovingColleagues();
            player = null;
            board = null;
            date = null;
            running = false;
            //todo save ranking
            return;
        }

        throw new RuntimeException("Game can't be stopped. It's not running.");
    }

    @Override
    public void addRandomVaccinationToBoard() {
        PlanVO plan = board.getPlan();
        CoordinatesVO coordinatesVO;
        do {
            int x = (int) (Math.random() * plan.getWidth());
            int y = (int) (Math.random() * plan.getLength());
            coordinatesVO = new CoordinatesVO(x, y);
        } while (board.addNewVaccination(coordinatesVO));
    }

    @Override
    public void stopMovingColleagues() {
        colleagueMovementTimer.cancel();
        colleagueMovementTimer = null;
    }

    @Override
    public void startMovingColleagues() {
        if (isRunning() && colleagueMovementTimer == null) {
            TimerTask rankingPointTask = new TimerTask() {
                public void run() {
                    board.getColleagues().forEach(ColleagueAggregate::nextPosition);
                    System.out.println("Colleagues have been moved.");
                }
            };
            colleagueMovementTimer = new Timer("Colleague Movement Timer");
            long delay = 10000L;
            colleagueMovementTimer.schedule(rankingPointTask, delay);
        }
    }

    @Override
    public List<RankingEntity> getTotalRankingForBoard() {
        return boardRepository.getTopRankingsByBoardName(board.getName());
    }
}

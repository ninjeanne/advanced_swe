package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.domainservice.GameDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Service
public class GameService implements GameDomainService {

    private final BoardService boardService;
    private final RankingService rankingService;
    private final PlayerService playerService;

    private RankingEntity rankingEntity;
    private Date date;
    private boolean running = false;

    private Timer rankingPointTimer;

    @Autowired
    public GameService(BoardService boardService, RankingService rankingService, PlayerService playerService) {
        this.boardService = boardService;
        this.rankingService = rankingService;
        this.playerService = playerService;
    }

    public void initializeGame(String playerName, String boardName) {
        if (!isRunning()) {
            initializeBoard(boardName);
            initializePlayer(playerName);
            initializeDate();
            initializeRanking();
            return;
        }

        throw new RuntimeException("Game can't be initialized because it's already running.");
    }

    public void initializeBoard(String boardName) {
        boardService.initializeBoard(boardName);
    }

    public void initializePlayer(String playerName) {
        playerService.initialize(playerName);
    }

    @Override
    public boolean isGameOver() {
        return !playerService.isAlive();
    }

    @Override
    public void initializeDate() {
        this.date = new Date();
    }

    @Override
    public void initializeRanking() {
        PlayerEntity currentPlayer = playerService.getCurrentPlayer();
        this.rankingEntity = new RankingEntity(UUID.randomUUID().toString(), currentPlayer.getName(), 0,
                currentPlayer.getWorkItems(), date);
    }

    @Override
    public boolean isInitialized() {
        return date != null && boardService.isInitialized() && playerService.isInitialized() && rankingEntity != null;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getLastRankingPointsForPlayer() {
        if (rankingEntity != null) {
            return rankingEntity.getTotal();
        }

        throw new RuntimeException("There is no saved ranking for this game");
    }

    @Override
    public void startCountingRankingPointsForPlayer() {
        if (isRunning() && rankingPointTimer == null) {
            TimerTask rankingPointTask = new TimerTask() {
                public void run() {
                    PlayerEntity currentPlayer = playerService.getCurrentPlayer();
                    rankingEntity = new RankingEntity(UUID.randomUUID().toString(), currentPlayer.getName(), rankingEntity.getEarned_points() + 1,
                           currentPlayer.getWorkItems(), rankingEntity.getDate());
                }
            };
            rankingPointTimer = new Timer("Increase Ranking Points");
            rankingPointTimer.scheduleAtFixedRate(rankingPointTask, 0, 500);
        }
    }

    @Override
    public void stopCountingRankingPointsForPlayer() {
        rankingPointTimer.cancel();
        rankingPointTimer = null;
    }

    @Override
    public boolean movePlayer(CoordinatesVO newCoordinates) {
        if (isRunning()) {
            if (boardService.isCoordinateEmpty(newCoordinates)) {
                playerService.setNewPosition(newCoordinates);

                if (boardService.isWorkItem(newCoordinates)) {
                    playerService.work();
                    boardService.addRandomWorkItemToBoard();
                }

                if (boardService.isVaccination(newCoordinates)) {
                    if (playerService.vaccinate()) {
                        boardService.addRandomVaccinationToBoard();
                    } else {
                        stopGame();
                    }
                }
                if (boardService.isInInfectionRadius(newCoordinates)) {
                    boolean isInfected = boardService.infectByProbability();
                    playerService.infect(isInfected);
                }

                return true;
            }
            return false;
        }
        throw new RuntimeException("Game hasn't been started yet.");
    }

    @Override
    public PlayerEntity getCurrentPlayer() {
        return playerService.getCurrentPlayer();
    }

    @Override
    public BoardAggregate getCurrentBoard() {
        return boardService.getCurrentBoard();
    }

    @Override
    public void startGame() {
        if (isInitialized()) {
            running = true;
            boardService.addRandomVaccinationToBoard();
            boardService.addRandomWorkItemToBoard();
            startCountingRankingPointsForPlayer();
            boardService.startMovingColleagues();
        }
    }

    @Override
    public void stopGame() {
        if (isRunning()) {
            if (rankingService.saveNewRankingForBoard(rankingEntity, boardService.getCurrentBoard().getName())) {
                stopCountingRankingPointsForPlayer();
                boardService.resetBoard();
                playerService.resetPlayer();
                date = null;
                running = false;
                rankingEntity = null;
                return;
            }
            throw new RuntimeException("Ranking couldn't be saved");
        }

        throw new RuntimeException("Game can't be stopped. It's not running.");
    }

}

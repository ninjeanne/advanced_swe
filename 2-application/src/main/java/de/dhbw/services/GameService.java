package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.domainservice.GameDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.ItemsVO;
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

    private PlayerEntity player;
    private RankingEntity rankingEntity;
    private Date date;
    private boolean running = false;

    private Timer rankingPointTimer;

    @Autowired
    public GameService(BoardService boardService, RankingService rankingService) {
        this.boardService = boardService;
        this.rankingService = rankingService;
    }

    public void initializeGame(String playerName, String boardName) {
        if (!isRunning()) {
            initializeBoard(boardName);
            initialize(new PlayerEntity(playerName, new CoordinatesVO(0, 0), new ItemsVO(3), new ItemsVO(0)));
            initializeDate();
            initializeRanking();
            return;
        }

        throw new RuntimeException("Game can't be initialized because it's already running.");
    }

    @Override
    public void initialize(PlayerEntity player) {
        if (!isRunning()) {
            this.player = player;
            this.player.setPosition(new CoordinatesVO(0, 0));
            return;
        }

        throw new RuntimeException("Player can't be changed when the game is running");
    }

    public void initializeBoard(String boardName) {
        boardService.initializeBoard(boardName);
    }

    @Override
    public boolean isGameOver() {
        return !player.isAlive();
    }

    @Override
    public void initializeDate() {
        this.date = new Date();
    }

    @Override
    public void initializeRanking() {
        this.rankingEntity = new RankingEntity(UUID.randomUUID().toString(), this.player.getName(), 0, player.getWorkItems(), date);
    }

    @Override
    public boolean isInitialized() {
        return date != null && boardService.isInitialized() && player != null && rankingEntity != null;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void playerHasWorked() {
        if (isRunning()) {
            player.increaseWorkItems();
            return;
        }
        throw new RuntimeException("Game hasn't been started yet.");
    }

    @Override
    public void vaccinatePlayer() {
        if (isRunning()) {
            if (player.isAlive()) {
                player.increaseLifePoints();
                return;
            }
            stopGame();
            return;
        }
        throw new RuntimeException("Game hasn't been started yet.");
    }

    @Override
    public void infectPlayer() {
        if (player.isAlive() && boardService.infectByProbability()) {
            player.decreaseLifePoints();
        }
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
                    rankingEntity = new RankingEntity(UUID.randomUUID().toString(), player.getName(), rankingEntity.getEarned_points() + 1,
                            player.getWorkItems(), rankingEntity.getDate());
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
                player.setPosition(newCoordinates);

                if (boardService.isWorkItem(newCoordinates)) {
                    playerHasWorked();
                    boardService.addRandomWorkItemToBoard();
                }

                if (boardService.isVaccination(newCoordinates)) {
                    vaccinatePlayer();
                    boardService.addRandomVaccinationToBoard();
                }
                if (boardService.isInInfectionRadius(newCoordinates)) {
                    infectPlayer();
                }

                return true;
            }
            return false;
        }
        throw new RuntimeException("Game hasn't been started yet.");
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public BoardAggregate getBoard() {
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
                player = null;
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

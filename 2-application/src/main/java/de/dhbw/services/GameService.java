package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.domainservice.GameDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameService implements GameDomainService {

    private final BoardService boardService;
    private final RankingService rankingService;
    private final PlayerService playerService;

    private boolean running = false;


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
    public boolean isInitialized() {
        return  boardService.isInitialized() && playerService.isInitialized();
    }

    @Override
    public boolean isRunning() {
        return running;
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
    public RankingEntity getCurrentRanking() {
        return playerService.getRankingEntity();
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
            playerService.startCountingRankingPoints();
            boardService.startMovingColleagues();
        }
    }

    @Override
    public void stopGame() {
        if (isRunning()) {
            if (rankingService.saveNewRankingForBoard(playerService.getRankingEntity(), boardService.getCurrentBoard().getName())) {
                boardService.reset();
                playerService.reset();
                running = false;
                return;
            }
            throw new RuntimeException("Ranking couldn't be saved");
        }

        throw new RuntimeException("Game can't be stopped. It's not running.");
    }

}

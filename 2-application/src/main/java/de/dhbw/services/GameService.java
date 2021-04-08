package de.dhbw.services;

import de.dhbw.domainservice.GameDomainService;
import de.dhbw.domainservice.InitializerDomainService;
import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.helper.GameAction;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService implements GameDomainService, InitializerDomainService {

    private final BoardService boardService;
    private final RankingService rankingService;
    private final PlayerService playerService;
    private final List<GameAction> gameActions;

    private boolean running = false;


    @Autowired
    public GameService(BoardService boardService, RankingService rankingService, PlayerService playerService, List<GameAction> gameActions) {
        this.boardService = boardService;
        this.rankingService = rankingService;
        this.playerService = playerService;
        this.gameActions = gameActions;
    }

    private void initializeGame(String playerName, String boardName) {
        if (!isRunning()) {
            boardService.initializeBoard(boardName);
            playerService.initialize(playerName);
            return;
        }

        throw new RuntimeException("Game can't be initialized because it's already running.");
    }

    @Override
    public boolean isGameOver() {
        return !playerService.isAlive();
    }

    @Override
    public boolean isInitialized() {
        return  boardService.isInitialized() && playerService.isInitialized();
    }

    /**
     * first argument will be used as boardName, the second one as player name
     * @param data
     */
    @Override
    public void initialize(String... data) {
        initializeGame(data[0], data[1]);
    }

    @Override
    public List<GameAction> getGameActions() {
        return gameActions;
    }

    @Override
    public boolean isRunning() {
        return running && getCurrentPlayer().isAlive();
    }

    @Override
    public boolean movePlayer(CoordinatesVO newCoordinates) {
        if (isRunning()) {
            if (boardService.isCoordinateEmpty(newCoordinates)) {
                for (GameAction gameAction : gameActions) {
                    gameAction.doActionOn(newCoordinates);
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
        return playerService.getRankingEntityForPlayer();
    }

    @Override
    public BoardEntity getCurrentBoard() {
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
            if (rankingService.saveNewRankingForBoard(playerService.getRankingEntityForPlayer(), boardService.getCurrentBoard().getName())) {
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

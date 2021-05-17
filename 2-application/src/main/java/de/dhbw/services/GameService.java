package de.dhbw.services;

import de.dhbw.domainservice.GameDomainService;
import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.GameObject;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.helper.GameAction;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService implements GameDomainService {

    private final BoardService boardService;
    private final RankingService rankingService;
    private final PlayerService playerService;
    private final List<GameAction> gameActions;
    private boolean running = false;

    @Autowired
    public GameService(BoardService boardService, RankingService rankingService, PlayerService playerService, List<GameObject> gameObjects,
            List<GameAction> gameActions) {
        this.boardService = boardService;
        this.rankingService = rankingService;
        this.playerService = playerService;
        this.gameActions = gameActions;
    }

    public void initialize(String boardName, String playerName) {
        if (!isRunning()) {
            boardService.initializeBoard(boardName);
            playerService.initialize(playerName, boardService.getGameObjects());
            return;
        }

        throw new RuntimeException("Game can't be initialized because it's already running.");
    }

    @Override
    public boolean isGameOver() {
        return !playerService.isAlive();
    }

    public boolean isInitialized() {
        return boardService.isInitialized() && playerService.isInitialized();
    }

    @Override
    public List<GameAction> getGameActions() {
        return gameActions;
    }

    @Override
    public boolean isRunning() {
        return running && getCurrentPlayer().isAlive();
    }

    private GameObject get(CoordinatesVO coordinate) {
        for (GameObject gameObject : boardService.getGameObjects()) {
            if (gameObject.isInRangeOfGameObject(coordinate)) {
                return gameObject;
            }
        }
        return null;
    }

    private GameAction<GameObject> getActionFor(GameObject gameObject) {
        for (GameAction<GameObject> gameAction : gameActions) {
            if (gameAction.getType().equals(gameObject.getClass())) {
                return gameAction;
            }
        }
        return null;
    }

    @Override
    public boolean movePlayer(CoordinatesVO newCoordinates) {
        if (isRunning()) {
            if (boardService.isCoordinateEmpty(newCoordinates)) {
                playerService.setNewPositionForPlayer(newCoordinates);
                doAction(newCoordinates);
                return true;
            }
            return false;
        }
        throw new RuntimeException("Game hasn't been started yet.");
    }

    private void doAction(CoordinatesVO newCoordinates) {
        GameObject gameObject = get(newCoordinates);
        if (gameObject != null) {
            GameAction<GameObject> gameAction = getActionFor(gameObject);
            if (gameAction != null) {
                gameAction.doActionOn(gameObject);
            }
        }
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
            playerService.startCountingRankingPoints();
            boardService.startMovingColleagues();
        }
    }

    @Override
    public void stopGame() {
        if (isRunning()) {
            if (rankingService.saveNewRanking(playerService.getRankingEntityForPlayer())) {
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

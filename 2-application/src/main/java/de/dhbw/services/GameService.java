package de.dhbw.services;

import de.dhbw.domainservice.GameDomainService;
import de.dhbw.domainservice.gameaction.GameAction;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.entities.board.BoardEntity;
import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GameService implements GameDomainService {

    private final BoardService boardService;
    private final HighscoreService highscoreService;
    private final PlayerService playerService;
    private final List<GameAction> gameActions;
    private boolean running = false;

    @Autowired
    public GameService(BoardService boardService, HighscoreService highscoreService, PlayerService playerService, List<GameAction> gameActions) {
        this.boardService = boardService;
        this.highscoreService = highscoreService;
        this.playerService = playerService;
        this.gameActions = gameActions;
    }

    @Override
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
        return !playerService.getCurrentPlayer().isAlive();
    }

    @Override
    public boolean isInitialized() {
        return boardService.isInitialized() && playerService.isInitialized();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    private GameObjectEntity get(CoordinatesVO coordinate) {
        for (GameObjectEntity gameObjectEntity : boardService.getGameObjects()) {
            if (gameObjectEntity.isInRangeOfGameObject(coordinate)) {
                return gameObjectEntity;
            }
        }
        return null;
    }

    private GameAction<GameObjectEntity> getActionFor(GameObjectEntity gameObjectEntity) {
        for (GameAction<GameObjectEntity> gameAction : gameActions) {
            if (gameAction.getType().equals(gameObjectEntity.getClass())) {
                return gameAction;
            }
        }
        return null;
    }

    @Override
    public boolean movePlayer(CoordinatesVO newCoordinates) {
        if (isRunning()) {
            if(!playerService.getCurrentPlayer().isAlive()){
                stopGame();
                return false;
            }
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
        GameObjectEntity gameObjectEntity = get(newCoordinates);
        if (gameObjectEntity != null) {
            GameAction<GameObjectEntity> gameAction = getActionFor(gameObjectEntity);
            if (gameAction != null) {
                gameAction.doActionOn(gameObjectEntity);
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
            if (highscoreService.saveNewRanking(playerService.getRankingEntityForPlayer())) {
                boardService.reset();
                playerService.reset();
                running = false;
                log.info("Ranking has been saved");
                return;
            }
            throw new RuntimeException("Ranking couldn't be saved");
        }

        throw new RuntimeException("Game can't be stopped. It's not running.");
    }

}

package de.dhbw.websocket;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.mapper.BoardMapper;
import de.dhbw.mapper.PlayerMapper;
import de.dhbw.services.GameService;
import de.dhbw.valueobjects.CoordinatesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@EnableScheduling
@Controller
@Slf4j
public class GameController {

    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final PlayerMapper playerMapper;
    private final BoardMapper boardMapper;

    @Autowired
    public GameController(SimpMessagingTemplate template, GameService gameService, PlayerMapper playerMapper, BoardMapper boardMapper) {
        this.template = template;
        this.gameService = gameService;
        this.playerMapper = playerMapper;
        this.boardMapper = boardMapper;
    }

    @MessageMapping("/start")
    public void startGame(String playerName) {
        String boardName = "default";
        gameService.initializeGame(playerName, boardName);
        log.info("New game {} initialized for player {}", boardName, playerName);

        if (gameService.isInitialized()) {
            if (!gameService.isRunning()) {
                gameService.startGame();
            }
        }
    }

    @MessageMapping("/stop")
    @SendTo("/backend/stop")
    public boolean stopGame() {
        if (gameService.isRunning()) {
            String playerName = gameService.getPlayer().getName();
            String boardName = gameService.getCurrentBoard().getName();
            gameService.stopGame();
            log.info("Game {} stopped for player {}", boardName, playerName);
            return true;
        }
        log.info("Game is already stopped.");
        return false;
    }

    @Scheduled(fixedRate = 500)
    public void rankingPoints() {
        if (gameService.isRunning()) {
            this.template.convertAndSend("/backend/ranking", gameService.getLastRankingPointsForPlayer());
        }
    }

    @MessageMapping("/move")
    @SendTo("/backend/player")
    public PlayerEntity movePlayer(CoordinatesVO coordinatesVO) {
        try {
            CoordinatesVO coordinates = new CoordinatesVO(coordinatesVO.getX(),
                    coordinatesVO.getY()); //TODO hier ein coordinates DTO einführen und richtig parsen
        } catch (Exception e) {
            return null;
        }
        if (gameService.isRunning()) {
            if (gameService.movePlayer(coordinatesVO)) {
                PlayerEntity playerEntity = gameService.getPlayer();
                if (gameService.isGameOver()) {
                    autoBackendAnswer();
                    this.template.convertAndSend("/backend/stop", stopGame());
                }
                return playerEntity;
            }
        }

        return null;
    }

    @Scheduled(fixedRate = 500)
    public void autoBackendAnswer() {
        if (gameService.isRunning()) {
            this.template.convertAndSend("/backend/board", gameService.getCurrentBoard());
        }
    }

}

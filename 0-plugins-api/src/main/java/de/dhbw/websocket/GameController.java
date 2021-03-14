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

    @MessageMapping("/initialize")
    public void initPlayer(String playerName) {
        String boardName = "default";
        gameService.initializeGame(playerName, boardName);
        log.info("New game {} initialized for player {}", boardName, playerName);
    }

    @MessageMapping("/stop")
    public void stopGame() {
        String playerName = gameService.getPlayer().getName();
        String boardName = gameService.getCurrentBoard().getName();
        gameService.stopGame();
        log.info("Game {} stopped for player {}", boardName, playerName);
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
                return gameService.getPlayer();
            }
            System.out.println("Nicht erlaubter Zug!");
            return null;
        }
        return null;
    }

    @Scheduled(fixedRate = 500)
    public void autoBackendAnswer() {
        if (gameService.isInitialized()) {
            if (!gameService.isRunning()) {
                gameService.startGame();
            }
            this.template.convertAndSend("/backend/start", gameService.getCurrentBoard());
        }
    }

}

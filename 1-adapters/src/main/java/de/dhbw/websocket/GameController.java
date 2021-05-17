package de.dhbw.websocket;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.mapper.BoardDTOMapper;
import de.dhbw.mapper.CoordinatesVOMapper;
import de.dhbw.mapper.PlayerDTOMapper;
import de.dhbw.mapper.RankingDTOMapper;
import de.dhbw.services.GameService;
import de.dhbw.services.HighscoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.stream.Collectors;

@EnableScheduling
@Controller
@Slf4j
public class GameController {

    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final PlayerDTOMapper playerDTOMapper;
    private final BoardDTOMapper boardDTOMapper;
    private final CoordinatesVOMapper coordinatesVOMapper;
    private final HighscoreService rankingService;
    private final RankingDTOMapper rankingDTOMapper;

    @Autowired
    public GameController(SimpMessagingTemplate template, GameService gameService, PlayerDTOMapper playerDTOMapper, BoardDTOMapper boardDTOMapper,
            CoordinatesVOMapper coordinatesVOMapper, HighscoreService rankingService, RankingDTOMapper rankingDTOMapper) {
        this.template = template;
        this.gameService = gameService;
        this.playerDTOMapper = playerDTOMapper;
        this.boardDTOMapper = boardDTOMapper;
        this.coordinatesVOMapper = coordinatesVOMapper;
        this.rankingService = rankingService;
        this.rankingDTOMapper = rankingDTOMapper;
    }

    @MessageMapping("/start")
    public void startGame(String playerName) {
        if (gameService.isInitialized()) {
            stopGame();
            log.warn("Stopping the running game...");
        }

        String boardName = "default";
        gameService.initialize(boardName, playerName);
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
            String playerName = gameService.getCurrentPlayer().getNameAsEntityID();
            gameService.stopGame();
            log.info("Game stopped for player {}", playerName);
            updateHighscore();
            return true;
        }
        log.info("Game has already been stopped.");
        return false;
    }

    @Scheduled(fixedRate = 500)
    public void rankingPoints() {
        if (gameService.isRunning()) {
            this.template.convertAndSend("/backend/ranking", gameService.getCurrentRanking().getTotal());
        }
    }

    @MessageMapping("/move")
    @SendTo("/backend/player")
    public PlayerDTO movePlayer(CoordinatesDTO coordinatesDTO) {
        try {
            if (gameService.isRunning()) {
                if (gameService.movePlayer(coordinatesVOMapper.apply(coordinatesDTO))) {
                    PlayerDTO playerDTO = playerDTOMapper.apply(gameService.getCurrentPlayer());
                    if (gameService.isGameOver()) {
                        autoBackendAnswer();
                        this.template.convertAndSend("/backend/stop", stopGame());
                    }
                    return playerDTO;
                }
            } else {
                stopGame();
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        return null;
    }

    @Scheduled(fixedRate = 500)
    public void autoBackendAnswer() {
        if (gameService.isRunning()) {
            this.template.convertAndSend("/backend/board", boardDTOMapper.apply(gameService.getCurrentBoard()));
        }
    }

    @Scheduled(fixedRate = 1000)
    public void updateHighscore() {
        this.template.convertAndSend("/backend/highscore",
                rankingService.getHighscore().stream().map(rankingDTOMapper).collect(Collectors.toList()));
        log.debug("Highscore updated");
    }

}

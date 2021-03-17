package de.dhbw.websocket;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.mapper.BoardDTOMapper;
import de.dhbw.mapper.CoordinatesVOMapper;
import de.dhbw.mapper.PlayerDTOMapper;
import de.dhbw.mapper.RankingDTOMapper;
import de.dhbw.services.GameService;
import de.dhbw.services.RankingService;
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
    private final RankingService rankingService;
    private final RankingDTOMapper rankingDTOMapper;

    @Autowired
    public GameController(SimpMessagingTemplate template, GameService gameService, PlayerDTOMapper playerDTOMapper, BoardDTOMapper boardDTOMapper,
            CoordinatesVOMapper coordinatesVOMapper, RankingService rankingService, RankingDTOMapper rankingDTOMapper) {
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
            updateRanking();
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
    public PlayerDTO movePlayer(CoordinatesDTO coordinatesDTO) {
        try {
            if (gameService.isRunning()) {
                if (gameService.movePlayer(coordinatesVOMapper.apply(coordinatesDTO))) {
                    PlayerDTO playerDTO = playerDTOMapper.apply(gameService.getPlayer());
                    if (gameService.isGameOver()) {
                        autoBackendAnswer();
                        this.template.convertAndSend("/backend/stop", stopGame());
                    }
                    return playerDTO;
                }
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
    public void updateRanking() {
        String boardName = "default";
        this.template.convertAndSend("/backend/topranking",
                rankingService.getTopRankingsForBoard(boardName).stream().map(rankingDTOMapper).collect(Collectors.toList()));
        log.debug("Top Rankings updated for board {}", boardName);
    }

}

package de.dhbw.controller;

import de.dhbw.dtos.CoordinateDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.mapper.CoordinateMapper;
import de.dhbw.mapper.PlayerMapper;
import de.dhbw.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final PlayerMapper playerMapper;
    private final CoordinateMapper coordinateMapper;

    @Autowired
    public GameController(GameService gameService, PlayerMapper playerMapper, CoordinateMapper coordinateMapper) {
        this.gameService = gameService;
        this.playerMapper = playerMapper;
        this.coordinateMapper = coordinateMapper;
    }

    @GetMapping("/init")
    public void init(@RequestBody PlayerDTO player) {
        gameService.initialize(playerMapper.apply(player));
        gameService.initDefaultBoard();
    }

    @GetMapping("/start")
    public void start() {
        gameService.start();
    }

    @GetMapping("/stop")
    public void stop() {
        gameService.stop();
    }

    @GetMapping("/move")
    public ResponseEntity<Object> move(CoordinateDTO coordinateDTO) {
        try {
            if (gameService.movePlayer(coordinateMapper.apply(coordinateDTO))) {
                return ResponseEntity.ok().body(gameService.getCurrentBoard());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

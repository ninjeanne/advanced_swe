package de.dhbw.utils;

import de.dhbw.helper.GameAction;
import de.dhbw.services.BoardService;
import de.dhbw.services.PlayerService;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnInfectionRadius implements GameAction {

    private final BoardService boardService;
    private final PlayerService playerService;

    @Autowired
    public ActionOnInfectionRadius(BoardService boardService, PlayerService playerService) {
        this.boardService = boardService;
        this.playerService = playerService;
    }

    @Override
    public void doActionOn(CoordinatesVO coordinatesVO) {
        if (boardService.isInInfectionRadius(coordinatesVO)) {
            boolean isInfected = boardService.infectByProbability();
            playerService.infect(isInfected);
        }
    }
}

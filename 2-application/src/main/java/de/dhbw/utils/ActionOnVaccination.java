package de.dhbw.utils;

import de.dhbw.helper.GameAction;
import de.dhbw.services.BoardService;
import de.dhbw.services.PlayerService;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnVaccination implements GameAction {

    private final BoardService boardService;
    private final PlayerService playerService;

    @Autowired
    public ActionOnVaccination(BoardService boardService, PlayerService playerService) {
        this.boardService = boardService;
        this.playerService = playerService;
    }

    @Override
    public void doActionOn(CoordinatesVO coordinatesVO) {
        if (boardService.isVaccination(coordinatesVO)) {
            if (playerService.vaccinate()) {
                boardService.addRandomVaccinationToBoard();
            }
        }
    }
}

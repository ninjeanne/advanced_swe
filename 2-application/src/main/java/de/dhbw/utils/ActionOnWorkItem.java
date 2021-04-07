package de.dhbw.utils;

import de.dhbw.helper.GameAction;
import de.dhbw.services.BoardService;
import de.dhbw.services.PlayerService;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionOnWorkItem implements GameAction {

    private final BoardService boardService;
    private final PlayerService playerService;

    @Autowired
    public ActionOnWorkItem(BoardService boardService, PlayerService playerService) {
        this.boardService = boardService;
        this.playerService = playerService;
    }

    @Override
    public void doActionOn(CoordinatesVO coordinatesVO) {
        if (boardService.isWorkItem(coordinatesVO)) {
            playerService.work();
            boardService.addRandomWorkItemToBoard();
        }
    }
}

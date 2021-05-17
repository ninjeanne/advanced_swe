package de.dhbw.helper;

import de.dhbw.domainservice.BoardDomainService;
import de.dhbw.domainservice.PlayerDomainService;
import de.dhbw.entities.GameObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class GameAction<T extends GameObject> {
    private final Class<T> type;
    @Autowired
    private PlayerDomainService playerService;
    @Autowired
    private BoardDomainService boardDomainService;

    public GameAction(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }

    public void doActionOn(T object) {
        object.doToPlayer(playerService.getCurrentPlayer());
        if (object.needsNewCoordinateAfterAction()) {
            object.setNewCoordinate(boardDomainService.getCurrentBoard().getBoardLayout().getRandomCoordinate());
        }
    }
}

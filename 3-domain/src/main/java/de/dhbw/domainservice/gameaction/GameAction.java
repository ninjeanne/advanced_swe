package de.dhbw.domainservice.gameaction;

import de.dhbw.domainservice.BoardDomainService;
import de.dhbw.domainservice.PlayerDomainService;
import de.dhbw.entities.gameobject.GameObjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * This is meant to be a Domain Service as it's implementing the logic between entities and multiple
 * services.
 * @param <T>
 */
@Service
public abstract class GameAction<T extends GameObjectEntity> {
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

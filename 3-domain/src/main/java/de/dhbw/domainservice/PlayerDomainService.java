package de.dhbw.domainservice;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface PlayerDomainService {
    void initialize(String playerName);
    boolean isInitialized();

    void setNewPosition(CoordinatesVO coordinatesVO);
    PlayerEntity getCurrentPlayer();
    boolean isAlive();

    boolean vaccinate();
    void work();
    void infect(boolean infectProbability);

    void resetPlayer();
}

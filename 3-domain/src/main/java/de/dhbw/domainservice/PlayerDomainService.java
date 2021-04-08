package de.dhbw.domainservice;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface PlayerDomainService {
    RankingEntity getRankingEntityForPlayer();

    void setNewPositionForPlayer(CoordinatesVO coordinatesVO);
    PlayerEntity getCurrentPlayer();
    boolean isAlive();

    boolean vaccinate();
    void work();
    void infect(boolean infectProbability);

    void reset();
}

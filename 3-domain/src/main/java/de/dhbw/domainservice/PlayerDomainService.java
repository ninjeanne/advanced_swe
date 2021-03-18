package de.dhbw.domainservice;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface PlayerDomainService {
    void initialize(String playerName);
    boolean isInitialized();

    void startCountingRankingPoints();
    RankingEntity getRankingEntity();

    void setNewPosition(CoordinatesVO coordinatesVO);
    PlayerEntity getCurrentPlayer();
    boolean isAlive();

    boolean vaccinate();
    void work();
    void infect(boolean infectProbability);

    void reset();
    void stopCountingRankingPoints();
}

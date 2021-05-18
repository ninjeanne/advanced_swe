package de.dhbw.domainservice;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.valueobjects.CoordinatesVO;

import java.util.List;

public interface PlayerDomainService extends CountRankingDomainService, ResetService, InitializerService{
    void initialize(String playerName, List<GameObjectEntity> gameObjectEntities);

    RankingEntity getRankingEntityForPlayer();

    void setNewPositionForPlayer(CoordinatesVO coordinatesVO);
    PlayerEntity getCurrentPlayer();

    boolean isAlive();
}

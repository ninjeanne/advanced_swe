package de.dhbw.entities.gameobject;

import de.dhbw.entities.player.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

@Getter
@Slf4j
public abstract class GameObjectEntity {
    private final String entityID;
    private CoordinatesVO coordinatesVO;

    public GameObjectEntity(String entityID) {
        this.entityID = entityID;
    }

    public GameObjectEntity() {
        this.entityID = UUID.randomUUID().toString();
    }

    public void doToPlayer(PlayerEntity playerEntity) {
        playerEntity.getPlayerStatistics().increaseStatistics(this.getClass());
    }

    public abstract int getRankingValue();

    public int getDefaultNumberOfObjects() {
        return 0;
    }

    public boolean needsNewCoordinateAfterAction() {
        return true;
    }

    public void setNewCoordinate(CoordinatesVO coordinatesVO) {
        if (needsNewCoordinateAfterAction()) {
            this.coordinatesVO = coordinatesVO;
        }
        log.warn("New coordinate couldn't be set for this type of game object");
    }

    public boolean isInRangeOfGameObject(CoordinatesVO coordinatesVO) {
        return this.coordinatesVO.equals(coordinatesVO);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameObjectEntity) {
            GameObjectEntity gameObjectEntity = (GameObjectEntity) obj;
            return this.entityID.equals(gameObjectEntity.entityID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityID);
    }
}

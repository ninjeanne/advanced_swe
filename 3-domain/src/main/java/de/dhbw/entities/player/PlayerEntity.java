package de.dhbw.entities.player;

import de.dhbw.aggregates.AggregateRoot;
import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.GameObjectCountVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PlayerEntity implements AggregateRoot {

    @NonNull
    private String nameAsEntityID;
    @NonNull
    private CoordinatesVO position;

    private PlayerStatisticsEntity playerStatistics;

    public PlayerEntity(String nameAsEntityID, CoordinatesVO position, List<GameObjectEntity> gameObjectEntityList) {
        this.nameAsEntityID = nameAsEntityID;
        this.position = position;
        Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems = new HashMap<>();
        for (GameObjectEntity gameObjectEntity : gameObjectEntityList) {
            statisticsPerItems.put(gameObjectEntity.getClass(), new GameObjectCountVO(gameObjectEntity.getDefaultNumberOfObjects()));
        }
        this.playerStatistics = new PlayerStatisticsEntity(statisticsPerItems);
    }

    public boolean isAlive(){
        return playerStatistics.isAlive();
    }

    /**
     * the new position is only allowed to differ in one coordinate and by one step!
     *
     * @param newPosition the new coordinates
     * @return true if the new position has been valid, otherwise false
     */
    public boolean isNewPosition(CoordinatesVO newPosition) {
        if (position.equals(newPosition)) {
            return true;
        }
        if (position.getY() == newPosition.getY()) {
            return position.getX() == (newPosition.getX() + 1) || position.getX() == (newPosition.getX() - 1);
        } else if (position.getX() == newPosition.getX()) {
            return position.getY() == (newPosition.getY() + 1) || position.getY() == (newPosition.getY() - 1);
        }
        return false;
    }

    public void setPosition(CoordinatesVO newPosition) {
        if (isNewPosition(newPosition)) {
            this.position = newPosition;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) obj;
            return this.getNameAsEntityID().equals(player.getNameAsEntityID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameAsEntityID);
    }
}

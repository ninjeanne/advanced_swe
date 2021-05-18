package de.dhbw.entities;

import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.entities.gameobject.Infection;
import de.dhbw.entities.gameobject.Vaccination;
import de.dhbw.valueobjects.GameObjectCountVO;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
public class PlayerStatisticsEntity {
    @Id
    private final String entityId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems = new HashMap<>();

    @SuppressWarnings("unused")
    public PlayerStatisticsEntity() {
        this.entityId = UUID.randomUUID().toString();
    }
    public PlayerStatisticsEntity(Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems){
        this();
        init(statisticsPerItems);
    }

    public PlayerStatisticsEntity(String entityId, Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems){
        this.entityId = entityId;
        init(statisticsPerItems);
    }

    private void init(Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems){
        this.statisticsPerItems = statisticsPerItems;
        initGameObjectIfNotExists(Infection.class);
        initGameObjectIfNotExists(Vaccination.class);
    }

    public GameObjectCountVO getStatistic(Class<? extends GameObjectEntity> gameObject) {
        initGameObjectIfNotExists(gameObject);
        return statisticsPerItems.get(gameObject);
    }

    private void initGameObjectIfNotExists(Class<? extends GameObjectEntity> gameObject){
        if(!statisticsPerItems.containsKey(gameObject)){
            statisticsPerItems.put(gameObject, new GameObjectCountVO(0));
        }
    }

    public void decreaseStatistics(Class<? extends GameObjectEntity> gameObject) {
        initGameObjectIfNotExists(gameObject);
        GameObjectCountVO oldValue = getStatistic(gameObject);
        this.statisticsPerItems.put(gameObject, oldValue.decreasedCount());
    }

    public void increaseStatistics(Class<? extends GameObjectEntity> gameObject) {
        initGameObjectIfNotExists(gameObject);
        GameObjectCountVO oldValue = getStatistic(gameObject);
        this.statisticsPerItems.put(gameObject, oldValue.increasedCount());
    }

    public boolean isAlive() {
        return statisticsPerItems.get(Vaccination.class).getCount() > statisticsPerItems.get(Infection.class).getCount();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerStatisticsEntity) {
            PlayerStatisticsEntity playerStatisticsEntity = (PlayerStatisticsEntity) obj;
            return this.getEntityId().equals(playerStatisticsEntity.getEntityId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }

}

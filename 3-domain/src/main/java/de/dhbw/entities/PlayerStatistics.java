package de.dhbw.entities;

import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.entities.gameobject.VaccinationEntity;
import de.dhbw.valueobjects.GameObjectCountVO;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Entity
public class PlayerStatistics {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems = new HashMap<>();

    @SuppressWarnings("unused")
    public PlayerStatistics() {
    }

    public PlayerStatistics(Map<Class<? extends GameObjectEntity>, GameObjectCountVO> statisticsPerItems){
        this.statisticsPerItems = statisticsPerItems;
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
        return statisticsPerItems.get(VaccinationEntity.class).equals(new GameObjectCountVO(0));
    }

}

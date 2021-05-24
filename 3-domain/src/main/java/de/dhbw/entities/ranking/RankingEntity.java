package de.dhbw.entities.ranking;

import de.dhbw.aggregates.AggregateRoot;
import de.dhbw.entities.player.PlayerStatisticsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@AllArgsConstructor
@Entity
@Slf4j
public class RankingEntity implements AggregateRoot {

    @NonNull
    @Id
    private String entityID;
    @NonNull
    @Column
    private String name;
    @Column
    private int earned_points;
    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PlayerStatisticsEntity playerStatistics;
    @NonNull
    @Column
    private Date date;

    @SuppressWarnings("unused")
    public RankingEntity() {
    }

    public int getEarned_points() {
        return earned_points;
    }

    public int getTotal() {
        AtomicInteger total = new AtomicInteger();
        playerStatistics.getStatisticsPerItems().forEach((gameObject, numberOfItems) -> {
            try {
                total.addAndGet(numberOfItems.getCount() * gameObject.newInstance().getRankingValue());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage());
            }
        });
        return earned_points + total.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RankingEntity) {
            RankingEntity rankingEntity = (RankingEntity) obj;
            return this.getEntityID().equals(rankingEntity.getEntityID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityID);
    }
}

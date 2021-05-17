package de.dhbw.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RankingEntity {

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
    private PlayerStatistics playerStatistics;
    @NonNull
    @Column
    private Date date;

    public int getEarned_points() {
        return earned_points;
    }

    public int getTotal() {
        AtomicInteger total = new AtomicInteger();
        playerStatistics.getStatisticsPerItems().forEach((key,value) -> {
            total.addAndGet(value.getCount() * 50); //todo andere bewertung als 50.. nicht jedes game object sollte einen wert haben
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

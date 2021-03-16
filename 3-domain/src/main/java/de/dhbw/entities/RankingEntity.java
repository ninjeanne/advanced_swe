package de.dhbw.entities;

import de.dhbw.valueobjects.ItemsVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RankingEntity {

    @NonNull
    @Id
    private String uuid;//entity id
    @NonNull
    @Column
    private String name;
    @Column
    private int earned_points;
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private ItemsVO workItems;
    @NonNull
    @Column
    private Date date;

    public int getEarned_points() {
        return earned_points;
    }

    public int getTotal() {
        return earned_points + workItems.getNumberOfItems() * 50;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RankingEntity) {
            RankingEntity rankingEntity = (RankingEntity) obj;
            return this.getUuid().equals(rankingEntity.getUuid());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

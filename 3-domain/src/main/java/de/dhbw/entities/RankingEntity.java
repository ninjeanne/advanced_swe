package de.dhbw.entities;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class RankingEntity {

    @NonNull
    private String uuid;//entity id
    private String name;
    private int earned_points;
    private Date date;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RankingEntity) {
            RankingEntity rankingEntity = (RankingEntity) obj;
            return this.getName().equals(rankingEntity.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

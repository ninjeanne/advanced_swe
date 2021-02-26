package de.dhbw.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Entity
@AllArgsConstructor
@IdClass(RankingVO.class)
public final class RankingVO implements Serializable {

    @NonNull
    @Id
    private final UserDetailsVO userDetails;
    @Id
    private final int earned_points;
    @Id
    @NonNull
    private final Date date;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RankingVO) {
            RankingVO rank = (RankingVO) obj;
            return this.userDetails.equals(rank.getUserDetails()) && this.earned_points == rank.getEarned_points() && this.date.equals(rank.date);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDetails, earned_points, date);
    }
}

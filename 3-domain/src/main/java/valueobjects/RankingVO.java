package valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor
public final class RankingVO {

    @NonNull
    private final UserDetailsVO userDetails;
    private final int earned_points;
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
